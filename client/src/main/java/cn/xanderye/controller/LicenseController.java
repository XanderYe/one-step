package cn.xanderye.controller;

import cn.xanderye.util.HardwareUtil;
import cn.xanderye.util.PropertyUtil;
import cn.xanderye.util.RSAEncrypt;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created on 2020/5/18.
 *
 * @author XanderYe
 */
public class LicenseController implements Initializable {
    @FXML
    private Label serialLabel;
    @FXML
    private Label expireDateLabel;
    @FXML
    private TextArea licenseTextArea;

    public static String licenseCode = "";

    public static JSONObject licenseJson = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String serial = HardwareUtil.getCpuId();
        serialLabel.setText(serial);
        licenseCode = PropertyUtil.get("license");
        if (licenseCode != null) {
            licenseTextArea.setText(licenseCode);
            try {
                InputStream inputStream = this.getClass().getResourceAsStream("/publicKey.keystore");
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String privateKey = bufferedReader.readLine();
                RSAPublicKey rsaPublicKey = RSAEncrypt.loadPublicKeyByStr(privateKey);
                byte[] res = RSAEncrypt.decrypt(rsaPublicKey, Base64.getDecoder().decode(licenseCode));
                licenseJson = JSON.parseObject(new String(res));
            } catch (Exception ignored) {
            }
        }
        if (licenseJson != null) {
            Date date = new Date(licenseJson.getString("expireDate"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            expireDateLabel.setText(sdf.format(date));
        } else {
            expireDateLabel.setText("未授权");
        }

    }

    public void submit() {
        String txt = licenseTextArea.getText();
        PropertyUtil.save("license", txt);
    }
}
