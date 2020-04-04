package cn.xanderye.controller;

import cn.xanderye.Login;
import cn.xanderye.Main;
import cn.xanderye.util.DNFUtil;
import cn.xanderye.util.HttpUtil;
import cn.xanderye.util.QQUtil;
import com.sun.javafx.robot.impl.FXRobotHelper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author XanderYe
 * @date 2020/2/6
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane rootLayout;
    @FXML
    private ImageView qrImage;
    @FXML
    private Label msg;
    @FXML
    private TextArea cookieText;

    /**
     * 应用ID
     */
    private final static String APP_ID = "21000127";
    private final static String DAID = "8";
    /**
     * 来源地址
     */
    private final static String U1 = "https://dnf.qq.com/gift.shtml";

    private static String qrsig = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InputStream inputStream = getQrCode();
        if (inputStream != null) {
            Image image = new Image(inputStream);
            qrImage.setImage(image);
            schedule(qrImage);
        } else {
            System.out.println("图片获取失败");
        }

        // 删除更新临时文件
        File file = new File("update/temp.jar");
        if (file.exists()) {
            file.delete();
        }
    }

    public void getCookie() {
        try {
            Runtime.getRuntime().exec("cmd /c start https://www.xanderye.cn/archives/uncategorized/122/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取二维码
     *
     * @return void
     * @author XanderYe
     * @date 2020-03-14
     */
    private InputStream getQrCode() {
        String t = Double.toString(Math.random());
        String url = "https://ssl.ptlogin2.qq.com/ptqrshow?appid=" + APP_ID + "&e=2&l=M&s=3&d=72&v=4&t=" + t + "&daid=" + DAID + "&pt_3rd_aid=0";
        byte[] data = HttpUtil.doDownload(url, null, null, null);
        if (data != null && data.length > 0) {
            Map<String, String> cookies = HttpUtil.getCookies();
            if (cookies != null) {
                qrsig = cookies.get("qrsig");
                return new ByteArrayInputStream(data);
            }
        }
        return null;
    }

    /**
     * 定时器
     *
     * @param imageView
     * @return void
     * @author XanderYe
     * @date 2020-03-14
     */
    private void schedule(ImageView imageView) {
        AtomicBoolean isLogin = new AtomicBoolean(false);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ScheduledExecutorService scheduledService = Executors.newSingleThreadScheduledExecutor();
        // 开一个线程挂后台
        executorService.execute(() -> {
            // 定时检查是否登录
            scheduledService.scheduleAtFixedRate(() -> {
                if (loginListener(imageView)) {
                    // 标志登录
                    isLogin.set(true);
                    // 隐藏窗口
                    Platform.runLater(() -> Login.stage.hide());
                    // 结束定时器
                    scheduledService.shutdown();
                }
            }, 0, 3, TimeUnit.SECONDS);
            try {
                scheduledService.awaitTermination(10, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 判断是否已登录
            if (isLogin.get()) {
                Platform.runLater(() -> {
                    Main main = new Main();
                    try {
                        main.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        executorService.shutdown();
        DNFUtil.getArea();
    }

    /**
     * 登录监听
     *
     * @param imageView
     * @return boolean
     * @author XanderYe
     * @date 2020-03-14
     */
    private boolean loginListener(ImageView imageView) {
        String cookieString = cookieText.getText();
        Map<String, Object> cookies = HttpUtil.formatCookies(cookieString);
        if (cookies != null && cookies.size() > 0) {
            DNFUtil.cookies = cookies;
            return true;
        } else {
            String ptqrtoken = QQUtil.hash33(qrsig);
            String action = "0-1-" + System.currentTimeMillis();
            String url = "https://ssl.ptlogin2.qq.com/ptqrlogin?u1=" + U1 + "&ptqrtoken=" + ptqrtoken + "&ptredirect=0&h=1&t=1&g=1&from_ui=1&ptlang=2052&action=" + action + "&js_ver=20021917&js_type=1&login_sig=&pt_uistyle=40&aid=" + APP_ID + "&daid=" + DAID + "&has_onekey=1";
            cookies = new HashMap<>();
            cookies.put("qrsig", qrsig);
            String result = HttpUtil.doGet(url, null, cookies, null);
            if (result.contains("二维码未失效")) {
                Platform.runLater(() -> msg.setText("二维码未失效"));

            } else if (result.contains("二维码认证中")) {
                Platform.runLater(() -> msg.setText("手机扫码成功"));
            } else if (result.contains("登录成功")) {
                DNFUtil.cookies.putAll(HttpUtil.getObjectCookies());
                return true;
            } else if (result.contains("二维码已失效")) {
                Platform.runLater(() -> msg.setText("二维码已失效， 重新生成"));
                InputStream inputStream = getQrCode();
                if (inputStream != null) {
                    imageView.setImage(new Image(inputStream));
                }
            } else {
                Platform.runLater(() -> msg.setText(result));
            }
        }
        return false;
    }
}
