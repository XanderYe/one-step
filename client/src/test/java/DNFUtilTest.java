import cn.xanderye.constant.Constant;
import cn.xanderye.entity.Payload;
import cn.xanderye.util.DNFUtil;
import cn.xanderye.util.HttpUtil;
import cn.xanderye.util.QQUtil;

/**
 * Created on 2020/4/7.
 *
 * @author 叶振东
 */
public class DNFUtilTest {
    public static void main(String[] args) {
        String url = "https://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=304044&sServiceDepartment=xinyue&sSDID=24d6a3160b98b7093b36a500c08be45e&sMiloTag=${sMiloTag}&_=1590465319090";
        String params = "gameId=&sArea=&iSex=&sRoleId=&iGender=&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=304044&iFlowId=664276&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20200428dnfbackflow%2F&eas_refer=http%3A%2F%2Fxinyue.qq.com%2Fcomm-htdocs%2Fmilo_mobile%2Flogin.html%3Freqid%3D7ca1f7ef-2470-474e-a8f5-f741ef84df59%26version%3D23&sServiceDepartment=xinyue";
        Payload payload = new Payload();
        payload.setMethod(1);
        payload.setInterfaceUrl(replaceString(url));
        payload.setParams(replaceString(params));
        String cookieString = "pgv_pvi=5830253568; pgv_pvid=4927550240; RK=vRzwD9jHcy; ptcz=681f21135416a87f3e878f32007caf22d63891dc803a5369bd134e108ebb8fca; PTTuserFirstTime=1584576000000; PTTosSysFirstTime=1584576000000; PTTosFirstTime=1584576000000; ts_uid=4927550240; eas_sid=Q1I5H8R4S5x938X8C8s3345916; tvfe_boss_uuid=51e9ee43199403dc; XWINDEXGREY=0; sd_userid=48731585894669276; sd_cookie_crttime=1585894669276; o_cookie=315695355; pac_uid=1_315695355; ptui_loginuin=315695355; gpmtips_data_his=%5B%7B%22id%22%3A%22318182%22%2C%22valid%22%3A1590395416%7D%5D; isOsDate=18402; ts_refer=xui.ptlogin2.qq.com/cgi-bin/xlogin%3Fproxy_url%3Dhttps%3A//game.qq.com/comm-htdocs/milo/proxy.html%26appid%3D21000127%26target%3Dtop%26s_url%3Dhtt; weekloop=19-20-21-22; pgv_info=ssid=s4684734020; pgv_si=s199652352; daojuqqcomrouteLine=malldownload; tokenParams=%3FADTAG%3DAD_gw.home.banner.1_dnf.20200522; _qpsvr_localtk=0.4013379734377245; uin=o0315695355; isHostDate=18408; isOsSysDate=18408; dnfqqcomrouteLine=a20190312welfare; luin=o0315695355; lskey=00010000b1877b98214308a3b85049b3198343e5d9e0c7f264c9c276a3a8b0f3168dd2f0b98b3cbc4e037c0d; xinyueqqcomrouteLine=a20200506jys_a20200428dnfbackflow_a20200428dnfbackflow_a20200428dnfbackflow; 5e246a6822b82bbf2b792b2cf39bb35e=315695355; ts_last=dnf.qq.com/gift.shtml; gpmtips_cfg=%7B%22iSendApi%22%3A0%2C%22iShowCount%22%3A0%2C%22iOnlineCount%22%3A2%2C%22iSendOneCount%22%3A0%2C%22iShowAllCount%22%3A0%2C%22iHomeCount%22%3A0%7D; skey=@89fmZWAGF; verifysession=h018ade7f2c4dfc9c97ba1595c65a42a215c456c36b1e42a17478d362b264a7e415e09497f8b3986ca6; eas_entry=https%3A%2F%2Fxui.ptlogin2.qq.com%2Fcgi-bin%2Fxlogin%3Fproxy_url%3Dhttps%3A%2F%2Fgame.qq.com%2Fcomm-htdocs%2Fmilo%2Fproxy.html%26appid%3D21000127%26target%3Dtop%26s_url%3Dhttps%253A%252F%252Fdnf.qq.com%252Fgift.shtml%26style%3D20%26daid%3D8";
        DNFUtil.cookies = HttpUtil.formatCookies(cookieString);
        DNFUtil.user.setAreaId(11);
        DNFUtil.user.setArea("浙江一区");
        DNFUtil.user.setCharacterNo("45832995");
        DNFUtil.user.setCharacterName("如灬花似月丶");
        DNFUtil.user.setSkey((String) DNFUtil.cookies.get("skey"));
        DNFUtil.user.setGTk(QQUtil.getGTK((String) DNFUtil.cookies.get("skey")));
        DNFUtil.user.setUin((String) DNFUtil.cookies.get("uin"));
        DNFUtil.user.setQq("315695355");
        try {
            DNFUtil.getCharacterList(11);
            for (int i=0;i< 50; i++) {
                String result =  DNFUtil.get(payload);
                System.out.println(result);
                Thread.sleep(2500);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String replaceString(String s) {
        if (s != null && !s.contains("${")) {
            StringBuilder sb = new StringBuilder(s);
            replace(sb, "sMiloTag=", Constant.S_MILO_TAG);
            replace(sb, "sArea=", Constant.AREA_ID);
            replace(sb, "partition=", Constant.AREA_ID);
            replace(sb, "area=", Constant.AREA_ID);
            replace(sb, "sRoleId=", Constant.CHARACTER_NO);
            replace(sb, "roleid=", Constant.CHARACTER_NO);
            replace(sb, "sRoleName=", Constant.CHARACTER_NAME);
            replace(sb, "g_tk=", Constant.GTK);
            replace(sb, "_=", Constant.RANDOM);
            replace(sb, "r=", Constant.RANDOM);
            replace(sb, "reqid=", Constant.UUID);
            replace(sb, "reqid%3d", Constant.UUID, "%26");
            replace(sb, "reqid%3D", Constant.UUID, "%26");
            replace(sb, "skey=", Constant.SKEY);
            replace(sb, "uin=", Constant.QQ);
            replace(sb, "md5str=", Constant.MD5_STR);
            replace(sb, "ams_checkparam=", Constant.AMS_CHECK_PARAM);
            replace(sb, "checkparam=", Constant.CHECK_PARAM);
            s = sb.toString();
        }
        return s;
    }

    private static StringBuilder replace(StringBuilder sb, String key, String replaceString) {
        return replace(sb, key, replaceString, null);
    }

    private static StringBuilder replace(StringBuilder sb, String key, String replaceString, String endString) {
        if (endString == null) {
            endString = "&";
        }
        if (sb.toString().contains(key)) {
            int start = sb.indexOf(key) + key.length();
            int end = sb.indexOf(endString, start);
            end = end == -1 ? sb.length() : end;
            if (start != end) {
                sb.replace(start, end, replaceString);
            }
        }
        return sb;
    }
}
