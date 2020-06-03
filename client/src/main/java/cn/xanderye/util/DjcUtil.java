package cn.xanderye.util;

import cn.xanderye.constant.Constant;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Map;

/**
 * Created on 2020/5/27.
 *
 * @author 叶振东
 */
public class DjcUtil {
    private static final String AESCipherMode = "AES/ECB/PKCS5Padding";
    public static final String AES_KEY = "84e6c6dc0f9p4a56";
    private static String RSA_ALGORITHM = "RSA";
    private static String RSA_ALGORITHM_MODEL = "RSA/ECB/PKCS1Padding";

    /**
     * 获取许愿列表
     * @param
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/6/3
     */
    public static String getDemandList() {
        String url = "https://djcapp.game.qq.com/daoju/igw/main/";
        String paramString = "_service=app.demand.user.demand&&weexVersion=0.9.4&platform=android&deviceModel=${deviceModel}&_app_id=1001&_biz_code=&pn=1&ps=5&appUid=${qq}&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTK}&osVersion=Android-25&ch=10000&sVersionName=v4.1.2.1&appSource=android&sDjcSign=${djcSign}";
        paramString = replaceParam(paramString);
        Map<String, Object> paramMap = HttpUtil.formatParameters(paramString);
        String result = HttpUtil.doGet(url, null, DNFUtil.cookies, paramMap);
        result = UnicodeUtil.unicodeStrToString(result);
        result = UrlUtil.decode(result);
        return result;
    }

    private static String replaceParam(String string) {
        if (string.contains(Constant.GTK)) {
            string = string.replace(Constant.GTK, DNFUtil.user.getGTk());
        }
        if (string.contains(Constant.QQ)) {
            string = string.replace(Constant.QQ, DNFUtil.user.getQq());
        }
        if (string.contains(Constant.DJC_SIGN)) {
            String sign = DjcUtil.djcSign(DNFUtil.user.getUin(), DNFUtil.user.getDeviceId());
            string = string.replace(Constant.DJC_SIGN, sign);
        }
        if (string.contains(Constant.DEVICE_ID)) {
            string = string.replace(Constant.DEVICE_ID, DNFUtil.user.getDeviceId());
        }
        if (string.contains(Constant.DEVICE_MODEL)) {
            string = string.replace(Constant.DEVICE_MODEL, DNFUtil.user.getDeviceModel());
        }
        return string;
    }

    public static String djcSign(String uin, String sDeviceId) {
        String paramString = null;
        try {
            paramString = QQUtil.uinToQQ(uin);
            long l2 = System.currentTimeMillis();
            int i = 102;
            paramString = paramString + "+" + sDeviceId + "+" + l2 + "+" + i;
            byte[] bytes = DjcUtil.aesEncrypt(paramString, DjcUtil.AES_KEY);
            bytes = DjcUtil.rsaEncrypt(bytes);
            paramString = DjcUtil.byte2hex(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return paramString;
    }

    public static String aesDecrypt(byte[] var0, String var1) throws Exception {
        if (var0 != null && var1 != null) {
            Cipher var2 = Cipher.getInstance("AES/ECB/PKCS5Padding");
            var2.init(2, new SecretKeySpec(var1.getBytes("utf-8"), "AES"));
            return new String(var2.doFinal(var0), "utf-8");
        } else {
            return null;
        }
    }

    public static byte[] aesEncrypt(String paramString1, String paramString2) throws Exception {
        if ((paramString1 == null) || (paramString2 == null)) {
            return null;
        }
        Cipher localCipher = Cipher.getInstance(AESCipherMode);
        localCipher.init(1, new SecretKeySpec(paramString2.getBytes(StandardCharsets.UTF_8), "AES"));
        return localCipher.doFinal(paramString1.getBytes(StandardCharsets.UTF_8));
    }

    public static PublicKey getRSAPublicKey() {
        try {
            InputStream inputStream = DjcUtil.class.getResourceAsStream("/djc_rsa_public_key_new.der");
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, length);
            }
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bos.toByteArray());
            return KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(keySpec);
        } catch (Exception e) {
            return null;
        }
    }

    public static byte[] rsaEncrypt(byte[] paramArrayOfByte) throws Exception {
        Cipher localCipher = Cipher.getInstance(RSA_ALGORITHM_MODEL);
        localCipher.init(1, getRSAPublicKey());
        return localCipher.doFinal(paramArrayOfByte);
    }

    public static String byte2hex(byte[] bytes) {
        String string = "";
        for(int i = 0; i < bytes.length; i++) {
            String hexString = Integer.toHexString(bytes[i] & 255);
            if (hexString.length() == 1) {
                string = string + "0" + hexString;
            } else {
                string = string + hexString;
            }
        }
        return string;
    }

    public static byte[] hex2byte(String var0) {
        if (var0 != null) {
            int var2 = var0.length();
            if (var2 % 2 != 1) {
                byte[] var3 = new byte[var2 / 2];

                for(int var1 = 0; var1 != var2 / 2; ++var1) {
                    var3[var1] = (byte)Integer.parseInt(var0.substring(var1 * 2, var1 * 2 + 2), 16);
                }
                return var3;
            }
        }
        return null;
    }

    public static void main(String[] args) throws Exception {
        String s = "9d2acbd1783914ddf4d1a1d7f9355ff2e28c62e4c427af0ae9c104b989ca8b094a0c80859c3d4879d30eee28fdae60b003acb9501139fd3cc1eabee0cb548429cc7071ee027c434d0ddb07751e5bba7f";
        byte[] bytes = hex2byte(s);
        String s1 = aesDecrypt(bytes, "84e6c6dc0f9p4a56");
        System.out.println(s1);
    }
}
