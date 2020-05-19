package cn.xanderye.controller;

import cn.xanderye.constant.Constant;
import cn.xanderye.license.License;
import cn.xanderye.util.HardwareUtil;
import cn.xanderye.util.PropertyUtil;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

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
    private Label expireDateLabel;
    @FXML
    private TextArea licenseTextArea;

    public static Stage stage;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
                    Date date = new Date(License.licenseJson.getLong("expireDate"));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    expireTxt = sdf.format(date);
                }
            } catch (Exception e) {
                log.error("msg", e);
            }

        }
        expireDateLabel.setText(expireTxt);
    }

    public void submit() {
        String txt = licenseTextArea.getText();
        PropertyUtil.save("license", txt);
        License.licenseCode = txt;
        License.install();
        stage.hide();
    }
}
