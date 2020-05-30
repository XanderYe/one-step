package cn.xanderye.controller;

import cn.xanderye.constant.Constant;
import cn.xanderye.license.License;
import cn.xanderye.util.HardwareUtil;
import cn.xanderye.util.PropertyUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created on 2020/5/18.
 *
 * @author XanderYe
 */
@Slf4j
public class LicenseController implements Initializable {
    @FXML
    private TextField serialText;
    @FXML
    private Label expireDateLabel, psLabel;
    @FXML
    private TextArea licenseTextArea;

    public static Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        psLabel.setText("价格：(2元/120万)/月，购买方式：\n1. 请支付宝转账<315695355@qq.com>并发送订单号和机器码到此邮箱\n2. 跨五邮寄100级剑帝<如灬花似月丶>并带上机器码和邮箱");
        String serial = HardwareUtil.getCpuId();
        serialText.setText(serial);
        License.licenseCode = PropertyUtil.get("license");
        if (License.licenseCode != null) {
            licenseTextArea.setText(License.licenseCode);
        }
        String expireTxt = "未授权";
        if (License.licenseJson != null) {
            try {
                String serialCode = License.licenseJson.getString("serial");
                if (serial.equals(serialCode) || Constant.GOD_LICENSE.equals(serialCode)) {
                    Long expireDate = License.licenseJson.getLong("expireDate");
                    if (License.systemTime > expireDate) {
                        expireTxt = "已过期";
                    } else {
                        Date date = new Date(expireDate);
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        expireTxt = sdf.format(date);
                    }
                }
            } catch (Exception e) {
                log.error("msg", e);
            }

        }
        expireDateLabel.setText(expireTxt);
    }

    public void submit() {
        String txt = licenseTextArea.getText();
        try {
            License.install(txt);
            License.licenseCode = txt;
            PropertyUtil.save("license", txt);
            stage.hide();
        } catch (Exception e) {
            Stage stage = new Stage();
            BorderPane root = new BorderPane();
            root.setPadding(new Insets(20, 30, 30, 30));
            Label label = new Label();
            label.setText("授权码错误");
            label.setFont(new Font(13));
            label.setWrapText(true);
            root.setCenter(label);
            stage.setTitle("提示");
            Scene scene = new Scene(root, 200, 100);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }
    }
}
