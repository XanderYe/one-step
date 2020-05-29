package cn.xanderye.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created on 2020/5/18.
 *
 * @author XanderYe
 */
public class HardwareUtil {
    public static String getCpuId() {
        String serial = null;
        try {
            Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});
            InputStream is = process.getInputStream();
            BufferedReader br;
            br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                String tmp = line.trim();
                if (tmp.length() > 0 && !"ProcessorId".equals(tmp)) {
                    serial = tmp;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serial;
    }

    public static String generateDeviceId() {
        String s = "00000000-" +
                randomCharacter(4) + "-" +
                randomCharacter(4) + "-" +
                randomCharacter(12);
        return s;
    }

    public static String generateDeviceModel() {
        String[] models = {"TAS-AN00", "LIO-AN00", "EVR-AN00", "MI 3", "MI 4", "MI 4c", "MI 5", "MI 5s", "MI6", "MI8", "MI9", "MI 10", "MI 10PRO", ""};
        return models[new Random().nextInt(models.length)];
    }

    public static String randomCharacter(int num){
        //声明一个StringBuffer存储随机数
        StringBuffer sb = new StringBuffer();
        char[] codeArray = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
                'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random random = new Random();
        for (int i = 0; i <num; i++){
            char n = codeArray[random.nextInt(codeArray.length)];
            sb.append(n);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateDeviceId());
        System.out.println(generateDeviceModel());
    }
}
