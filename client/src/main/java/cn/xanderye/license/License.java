package cn.xanderye.license;

import cn.xanderye.constant.Constant;
import cn.xanderye.util.HardwareUtil;
import cn.xanderye.util.HttpUtil;
import cn.xanderye.util.RSAUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created on 2020/5/19.
 *
 * @author 叶振东
 */
@Slf4j
public class License {
    public static Long systemTime;

    public static String serial;

    public static String licenseCode = "";

    public static JSONObject licenseJson = null;

    static {
        systemTime = System.currentTimeMillis();
        serial = HardwareUtil.getCpuId();
    }

    public static void install() throws BadPaddingException, NullPointerException, IllegalArgumentException {
        License.install(null);
    }

    public static void install(String license) throws BadPaddingException, NullPointerException, IllegalArgumentException {
        if (license == null) {
            license = licenseCode;
        }
        try {
            InputStream inputStream = License.class.getResourceAsStream("/publicKey.keystore");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String privateKey = bufferedReader.readLine();
            RSAPublicKey rsaPublicKey = RSAUtil.loadPublicKeyByStr(privateKey);
            byte[] res = RSAUtil.decrypt(rsaPublicKey, Base64.getDecoder().decode(license));
            licenseJson = JSON.parseObject(new String(res));
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | InvalidKeySpecException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public static void getSystemTime() {
        String result = HttpUtil.doGet(Constant.SYSTEM_TIME_URL, null);
        JSONObject resultJson = JSON.parseObject(result);
        String data = resultJson.getString("data");
        if (data != null) {
            systemTime = Long.parseLong(data);
        }
    }
}
