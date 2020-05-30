package cn.xanderye.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QQUtil {

    public static String hash33(String qrsig) {
        int e = 0;
        for (int i = 0; i < qrsig.length(); i++) {
            e += (e << 5) + qrsig.charAt(i);
        }
        return String.valueOf(2147483647 & e);
    }

    public static String getGTK(String skey) {
        int hash = 5381;
        for (int i = 0; i < skey.length(); ++i) {
            hash += (hash << 5) + skey.charAt(i);
        }
        return String.valueOf(hash & 0x7fffffff);
    }

    public static String getCallback() {
        return System.currentTimeMillis() + String.valueOf(new Random().nextInt(100000));
    }

    public static String sMiloTag(String iActivityId, String iFlowId, String id) {
        StringBuilder arrMiloTag = new StringBuilder();
        arrMiloTag.append("AMS-MILO-");
        arrMiloTag.append(iActivityId).append("-");
        arrMiloTag.append(iFlowId).append("-");
        arrMiloTag.append(id).append("-");
        arrMiloTag.append(System.currentTimeMillis()).append("-");
        String sRandomMask = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sRandomVal = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int rIndex = new Random().nextInt(62);
            sRandomVal.append(sRandomMask, rIndex, rIndex + 1);
        }
        arrMiloTag.append(sRandomVal.toString());
        return arrMiloTag.toString();
    }

    public static String uinToQQ(String uin) {
        int startIndex = 1;
        for (int i = 1; i < uin.length(); i++) {
            if (uin.charAt(i) != '0') {
                break;
            }
            startIndex++;
        }
        return uin.substring(startIndex);
    }

    public static void main(String[] args) {
        System.out.println(uinToQQ("o1025251531"));
        System.out.println(uinToQQ("o0315695355"));
    }
}
