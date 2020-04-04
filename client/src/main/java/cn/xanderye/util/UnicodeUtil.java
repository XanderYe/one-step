package cn.xanderye.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeUtil {
    /**
     * Unicode转中文
     * @param unicode
     * @return java.lang.String
     * @author XanderYe
     * @date 2020-03-29
     */
    public static String unicodeToString(String unicode) {
        String[] uns = unicode.split("\\\\u");
        StringBuilder returnStr = new StringBuilder();
        for (int i = 1; i < uns.length; i++) {
            returnStr.append((char) Integer.valueOf(uns[i], 16).intValue());
        }
        return returnStr.toString();
    }

    /**
     * 中文转Unicode
     * @param cn
     * @return java.lang.String
     * @author XanderYe
     * @date 2020-03-29
     */
    public static String stringToUnicode(String cn) {
        char[] chars = cn.toCharArray();
        StringBuilder returnStr = new StringBuilder();
        for (char aChar : chars) {
            returnStr.append("\\u").append(Integer.toString(aChar, 16));
        }
        return returnStr.toString();
    }

    /**
     * 含有Unicode的字符串转中文
     * @param unicodeStr
     * @return java.lang.String
     * @author XanderYe
     * @date 2020-03-29
     */
    public static String unicodeStrToString(String unicodeStr) {
        int length = unicodeStr.length();
        int count = 0;
        String regex = "\\\\u[a-f0-9A-F]{1,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(unicodeStr);
        StringBuilder sb = new StringBuilder();

        while(matcher.find()) {
            String oldChar = matcher.group();
            String newChar = unicodeToString(oldChar);
            int index = matcher.start();

            sb.append(unicodeStr, count, index);
            sb.append(newChar);
            count = index+oldChar.length();
        }
        sb.append(unicodeStr, count, length);
        return sb.toString();
    }
}
