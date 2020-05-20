package cn.xanderye.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Created on 2020/5/19.
 *
 * @author XanderYe
 */
public class RSAUtil {
    /**
     * 随机生成密钥对
     *
     * @param filePath
     * @return void
     * @author XanderYe
     * @date 2020/5/19
     */
    public static void genKeyPair(String filePath) throws IOException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (keyPairGen != null) {
            // 初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(1024, new SecureRandom());
            // 生成一个密钥对，保存在keyPair中
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 得到私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 得到公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 得到公钥字符串
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            // 得到私钥字符串
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            // 将密钥对写入到文件
            FileWriter pubFw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter priFw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubBw = new BufferedWriter(pubFw);
            BufferedWriter priBw = new BufferedWriter(priFw);
            pubBw.write(publicKeyString);
            priBw.write(privateKeyString);
            pubBw.flush();
            pubBw.close();
            pubFw.close();
            priBw.flush();
            priBw.close();
            priFw.close();
        }
    }

    /**
     * 从文件中加载公钥
     *
     * @param path
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/5/19
     */
    public static String loadPublicKeyByFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path + File.separator + "publicKey.keystore"));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            sb.append(readLine);
        }
        br.close();
        return sb.toString();
    }

    /**
     * 从字符串中加载公钥
     *
     * @param publicKeyStr
     * @return java.security.interfaces.RSAPublicKey
     * @author XanderYe
     * @date 2020/5/19
     */
    public static RSAPublicKey loadPublicKeyByStr(String publicKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (publicKeyStr != null) {
            byte[] buffer = Base64.getDecoder().decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        }
        return null;
    }

    /**
     * 从文件中加载私钥
     *
     * @param path
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/5/19
     */
    public static String loadPrivateKeyByFile(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(path + File.separator + "privateKey.keystore"));
        String readLine;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            sb.append(readLine);
        }
        br.close();
        return sb.toString();
    }

    /**
     * 从字符串中加载私钥
     *
     * @param privateKeyStr
     * @return java.security.interfaces.RSAPrivateKey
     * @author XanderYe
     * @date 2020/5/19
     */
    public static RSAPrivateKey loadPrivateKeyByStr(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        if (privateKeyStr != null) {
            byte[] buffer = Base64.getDecoder().decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        }
        return null;
    }

    /**
     * 使用公钥加密
     * @param publicKey
     * @param plainTextData
     * @return byte[]
     * @author XanderYe
     * @date 2020/5/19
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (publicKey == null) {
            return null;
        }
        // 使用默认RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(plainTextData);
    }

    /**
     * 使用私钥加密
     * @param privateKey
     * @param plainTextData
     * @return byte[]
     * @author XanderYe
     * @date 2020/5/19
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] plainTextData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        if (privateKey == null) {
            return null;
        }
        // 使用默认RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(plainTextData);
    }

    /**
     * 使用私钥解密
     * @param privateKey
     * @param cipherData
     * @return byte[]
     * @author XanderYe
     * @date 2020/5/19
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        if (privateKey == null) {
            return null;
        }
        // 使用默认RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(cipherData);
    }

    /**
     * 使用公钥解密
     * @param publicKey
     * @param cipherData
     * @return byte[]
     * @author XanderYe
     * @date 2020/5/19
     */
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] cipherData)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
        if (publicKey == null) {
            return null;
        }
        // 使用默认RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(cipherData);
    }
}
