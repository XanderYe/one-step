import cn.xanderye.entity.Payload;
import cn.xanderye.util.DNFUtil;
import cn.xanderye.util.HttpUtil;

/**
 * Created on 2020/4/7.
 *
 * @author 叶振东
 */
public class DNFUtilTest {
    public static void main(String[] args) {
        Payload payload = new Payload();
        payload.setMethod(1);
        payload.setInterfaceUrl("http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true");
        //payload.setHeaders("Referer:https://guanjia.qq.com/act/cop/202003dnf/?ADTAG=cop.dnfxsy.youxi.banner");
        payload.setParams("iActivityId=166962&iFlowId=512393&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223");
        String cookieString = "pgv_pvi=5830253568; pgv_pvid=4927550240; RK=vRzwD9jHcy; ptcz=681f21135416a87f3e878f32007caf22d63891dc803a5369bd134e108ebb8fca; PTTosSysFirstTime=1584576000000; PTTuserFirstTime=1584576000000; PTTosFirstTime=1584576000000; eas_sid=Q1I5H8R4S5x938X8C8s3345916; tvfe_boss_uuid=51e9ee43199403dc; XWINDEXGREY=0; sd_userid=48731585894669276; sd_cookie_crttime=1585894669276; isHostDate=18359; isOsSysDate=18359; isOsDate=18359; weekloop=12-0-14-15; o_cookie=315695355; pac_uid=1_315695355; _qpsvr_localtk=0.31929163310470354; pgv_si=s6881472512; pgv_info=ssid=s9183411091&pgvReferrer=; eas_entry=https%3A%2F%2Ftool.helper.qq.com%2Fv3%2Fdnf%2Fofficial_website%2Findex.shtml; ied_rf=tool.helper.qq.com/v3/dnf/official_website/index.shtml; midas_openkey=@UqCMPr2Bd; midas_openid=315695355; xinyueqqcomrouteLine=a20181101rights_a20181101rights_a20181101rights_a20181101rights_a20181101rights_a20181101rights_a20181101rights; d7a9c0c275a4b8c94cf7397cc1d8bcc8=315695355; ts_last=dnf.qq.com/gift.shtml; gpmtips_cfg=%7B%22iSendApi%22%3A0%2C%22iShowCount%22%3A0%2C%22iOnlineCount%22%3A2%2C%22iSendOneCount%22%3A0%2C%22iShowAllCount%22%3A0%2C%22iHomeCount%22%3A0%7D; verifysession=h018d323e165a48ef49e1563f4f0207271c54a5a85ecf8a967345bda7316d825bcc14241092781261fb; recommend_init=1; rec_req_ctips_dnf=1; ptui_loginuin=202451454; uin=o0202451454; skey=@82GwbgNZm";
        DNFUtil.cookies = HttpUtil.formatCookies(cookieString);
        DNFUtil.character.setAreaId(11);
        DNFUtil.character.setCharacterNo("47807962");
        DNFUtil.character.setCharacterName("大狗又空辣");
        try {
            String result =  DNFUtil.get(payload);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
