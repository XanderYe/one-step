package cn.xanderye.controller;

import cn.xanderye.constant.Constant;
import cn.xanderye.entity.Version;
import cn.xanderye.util.HttpUtil;
import cn.xanderye.util.ZipUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2020/4/2.
 *
 * @author XanderYe
 */
public class UpdateController implements Initializable {

    @FXML
    private Label versionLabel, timeLabel, contentLabel;
    @FXML
    private Button updateButton;
    @FXML
    private ProgressBar progressBar;

    public static Version version = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (version != null) {
            versionLabel.setText(version.getVersion());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeLabel.setText(sdf.format(version.getDate()));
            contentLabel.setText(version.getDesc());
        }
    }

    public void update() {
        updateButton.setDisable(true);
        progressBar.setVisible(true);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            byte[] bytes = HttpUtil.doDownloadProgress(Constant.UPDATE_URL, null, null, null, progressBar);
            File file = new File(System.getProperty("user.dir") + File.separator + "update/temp.zip");
            file.getParentFile().mkdirs();
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            try {
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bos != null) {
                        bos.close();
                    }
                    if (fos != null) {
                        fos.close();
                    }
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                ZipUtil.unzip("update/temp.zip", System.getProperty("user.dir"));
                Runtime.getRuntime().exec(".\\jre\\bin\\java -jar .\\jre\\main.jar");
                System.exit(-1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
    }
}
