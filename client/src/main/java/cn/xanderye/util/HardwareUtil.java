package cn.xanderye.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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

    public static void main(String[] args) {
        String serial = getCpuId();
        System.out.println(serial);
    }
}
