package cn.xanderye.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created on 2020/4/22.
 *
 * @author 叶振东
 */
public class UrlUtil {

    /**
     * url编码
     * @param s
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/4/22
     */
    public static String encode(String s) {
        try {
            s = URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * url解码
     * @param s
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/4/22
     */
    public static String decode(String s) {
        try {
            s = URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * url编码两次
     * @param s
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/4/22
     */
    public static String doubleEncode(String s) {
        try {
            s = URLEncoder.encode(URLEncoder.encode(s, "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * url解码两次
     * @param s
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/4/22
     */
    public static String doubleDecode(String s) {
        try {
            s = URLDecoder.decode(URLDecoder.decode(s, "UTF-8"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s;
    }
}
