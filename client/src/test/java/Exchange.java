import cn.xanderye.entity.Character;
import cn.xanderye.entity.Payload;
import cn.xanderye.util.DNFUtil;
import cn.xanderye.util.HttpUtil;

/**
 * Created on 2020/4/13.
 *
 * @author 叶振东
 */
public class Exchange {
    public static void main(String[] args) {
        Payload payload = new Payload();
        payload.setMethod(1);
        payload.setInterfaceUrl("http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true");
        payload.setParams("gameId=&sArea=&iSex=&sRoleId=&iGender=&package_id=702218&sServiceType=tgclub&objCustomMsg=&areaname=&roleid=&rolelevel=&rolename=&areaid=&iActivityId=166962&iFlowId=512469&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fnoreferrer%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_${random}");
        //String cookieString = "pgv_pvi=5830253568; pgv_pvid=4927550240; RK=vRzwD9jHcy; ptcz=681f21135416a87f3e878f32007caf22d63891dc803a5369bd134e108ebb8fca; eas_sid=Q1I5H8R4S5x938X8C8s3345916; tvfe_boss_uuid=51e9ee43199403dc; XWINDEXGREY=0; isHostDate=18355; isOsDate=18355; isOsSysDate=18355; PTTuserFirstTime=1585872000000; PTTosSysFirstTime=1585872000000; PTTosFirstTime=1585872000000; weekloop=0-0-0-14; sd_userid=48731585894669276; sd_cookie_crttime=1585894669276; o_cookie=315695355; pac_uid=1_315695355; ptui_loginuin=315695355; ied_qq=o0315695355; pgv_info=ssid=s5751461672; _qpsvr_localtk=0.06170073137092036; pgv_si=s6289848320; uin=o0315695355; skey=@pVH1LyKTv; p_uin=o0315695355; pt4_token=E8o9e7g-isdrUdnSIZS5FkSLO9HzIkEjTx-Ktxh0QG8_; p_skey=3hOY8DSvjFHWORIJy1qhzKm5si2LfI1EHwsoZKXgfxc_; pt2gguin=o0315695355; IED_LOG_INFO2=userUin%3D315695355%26nickName%3DXander%26userLoginTime%3D1586747096; verifysession=h0192b1e9f60e95865b2c09857fe49d96c4503ef6ed1af4848076d21fc82629c3c83d0e2aab8d27f757; xinyueqqcomrouteLine=a20200303dnfbjbb_a20181101rights_a20181101rights";
        String cookieString = "pgv_info=ssid=s8751333686; pgv_pvid=55001336; xinyueqqcomrouteLine=a20181101rights; eas_sid=s1f5o83677I571p5U8d7B2u8K8; pgv_pvi=491993984; pgv_si=s7587315712; _qpsvr_localtk=0.7305512769296598; ts_last=dnf.qq.com/gift.shtml; ts_uid=485499840; gpmtips_cfg=%7B%22iSendApi%22%3A0%2C%22iShowCount%22%3A0%2C%22iOnlineCount%22%3A2%2C%22iSendOneCount%22%3A0%2C%22iShowAllCount%22%3A0%2C%22iHomeCount%22%3A0%7D; verifysession=h019e62ceeebbd4ad295b0a57d7e190f275d5d585272a248f84d47490c5193e6f39358803d4b043e45c; rec_req_ctips_dnf=1; ptui_loginuin=401964219; uin=o0401964219; skey=@fYAHTBrpX; RK=fUy1DRH2fD; ptcz=c714f672eb1967a13fba87d9efa038e2f474692de731327b7c9a7533cfc7b532; d7a9c0c275a4b8c94cf7397cc1d8bcc8=401964219";
        DNFUtil.cookies = HttpUtil.formatCookies(cookieString);
        DNFUtil.areaId = 11;
        DNFUtil.character = new Character();
        //DNFUtil.character.setCharacterNo("45832995");
        DNFUtil.character.setCharacterNo("51327324");
        //DNFUtil.character.setCharacterName("如灬花似月丶");
        DNFUtil.character.setCharacterName("帝皇龙甲兽丶");
        try {
            for (int i=0;i< 50; i++) {
                String result = DNFUtil.get(payload);
                System.out.println(result);
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
