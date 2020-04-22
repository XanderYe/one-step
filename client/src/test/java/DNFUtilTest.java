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
        Payload payload = new Payload();
        payload.setMethod(1);
        payload.setInterfaceUrl("http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=dnf&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true");
        payload.setParams("sServiceType=dnf&user_area=${areaId}&user_roleId=${characterNo}&user_roleName=${userRoleId}&user_areaName=${userAreaName}&user_roleLevel=100&user_checkparam=${checkParam}&user_md5str=${md5Str}&user_sex=&user_platId=&user_partition=11&iActivityId=166962&iFlowId=512491&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_${random}");
        String cookieString = "pgv_pvi=5830253568; RK=vRzwD9jHcy; ptcz=681f21135416a87f3e878f32007caf22d63891dc803a5369bd134e108ebb8fca; PTTosSysFirstTime=1584576000000; PTTuserFirstTime=1584576000000; PTTosFirstTime=1584576000000; eas_sid=Q1I5H8R4S5x938X8C8s3345916; tvfe_boss_uuid=51e9ee43199403dc; XWINDEXGREY=0; sd_userid=48731585894669276; sd_cookie_crttime=1585894669276; o_cookie=315695355; pac_uid=1_315695355; ptui_loginuin=315695355; luin=o0315695355; lskey=00010000a1855fb7d2ce1fd7855b3a4b46905e4780a93c90fe906972f0f9ce53c15d99486c07a06adbec92bd; isOsDate=18369; pgv_si=s2159021056; _qpsvr_localtk=0.4012749019216171; xinyueqqcomrouteLine=a20181101rights_a20181101rights_a20181101rights; uin=o0315695355; isHostDate=18374; isOsSysDate=18374; dnfqqcomrouteLine=a20200413comic; weekloop=14-15-16-17; tokenParams=%3FADTAG%3Dpc.off.web.main.top%26e_code%3D506897%26idataid%3D308929; d7a9c0c275a4b8c94cf7397cc1d8bcc8=315695355; ts_last=dnf.qq.com/gift.shtml; gpmtips_cfg=%7B%22iSendApi%22%3A0%2C%22iShowCount%22%3A0%2C%22iOnlineCount%22%3A2%2C%22iSendOneCount%22%3A0%2C%22iShowAllCount%22%3A0%2C%22iHomeCount%22%3A0%7D; verifysession=h0169d8f234c7eedf2d7e1561d9327ffc1bf623d56a98dfe9d7a2078419a93dbb497d4ff3212c72bc55; recommend_init=1; rec_req_ctips_dnf=1; skey=@PKmpRYHZk";
        DNFUtil.cookies = HttpUtil.formatCookies(cookieString);
        DNFUtil.user.setAreaId(11);
        DNFUtil.user.setArea("浙江一区");
        DNFUtil.user.setCharacterNo("45832995");
        DNFUtil.user.setCharacterName("如灬花似月丶");
        DNFUtil.user.setSkey((String) DNFUtil.cookies.get("skey"));
        DNFUtil.user.setGTk(QQUtil.getGTK((String) DNFUtil.cookies.get("skey")));
        DNFUtil.user.setUin((String) DNFUtil.cookies.get("uin"));
        try {
            DNFUtil.getCharacterList(11);
            String result =  DNFUtil.get(payload);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
