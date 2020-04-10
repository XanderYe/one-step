package cn.xanderye.util;

import cn.xanderye.constant.Constant;
import cn.xanderye.entity.Character;
import cn.xanderye.entity.Payload;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DNFUtil {
    private static Logger logger = LoggerFactory.getLogger(DNFUtil.class);

    /**
     * 登录cookie
     */
    public static Map<String, Object> cookies = new HashMap<>(16);

    /**
     * 大区数组
     */
    public static JSONArray areaArray = JSON.parseArray("[{t:\"广东\",v:\"21\",opt_data_array:[{t:\"广东一区\",v:\"1\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东二区\",v:\"15\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东三区\",v:\"22\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东四区\",v:\"45\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东五区\",v:\"52\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东六区\",v:\"65\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东七区\",v:\"71\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东八区\",v:\"81\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东九区\",v:\"89\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东十区\",v:\"98\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东十一区\",v:\"105\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东十二区\",v:\"126\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广东十三区\",v:\"134\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"福建\",v:\"33\",opt_data_array:[{t:\"福建一区\",v:\"14\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"福建二区\",v:\"44\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"福建3/4区\",v:\"80\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"浙江\",v:\"30\",opt_data_array:[{t:\"浙江一区\",v:\"11\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"浙江二区\",v:\"21\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"浙江三区\",v:\"55\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"浙江4/5区\",v:\"84\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"浙江六区\",v:\"116\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"浙江七区\",v:\"129\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"北京\",v:\"22\",opt_data_array:[{t:\"北京一区\",v:\"2\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"北京2/4区\",v:\"35\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"北京三区\",v:\"72\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"上海\",v:\"23\",opt_data_array:[{t:\"上海一区\",v:\"3\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"上海二区\",v:\"16\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"上海三区\",v:\"36\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"上海4/5区\",v:\"93\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"四川\",v:\"24\",opt_data_array:[{t:\"四川一区\",v:\"4\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"四川二区\",v:\"26\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"四川三区\",v:\"56\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"四川四区\",v:\"70\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"四川五区\",v:\"82\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"四川六区\",v:\"107\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"湖南\",v:\"25\",opt_data_array:[{t:\"湖南一区\",v:\"5\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖南二区\",v:\"25\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖南三区\",v:\"50\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖南四区\",v:\"66\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖南五区\",v:\"74\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖南六区\",v:\"85\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖南七区\",v:\"117\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"山东\",v:\"26\",opt_data_array:[{t:\"山东一区\",v:\"6\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"山东2/7区\",v:\"37\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"山东三区\",v:\"59\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"山东四区\",v:\"75\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"山东五区\",v:\"78\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"山东六区\",v:\"106\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"江苏\",v:\"27\",opt_data_array:[{t:\"江苏一区\",v:\"7\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江苏二区\",v:\"20\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江苏三区\",v:\"41\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江苏四区\",v:\"53\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江苏5/7区\",v:\"79\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江苏六区\",v:\"90\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江苏八区\",v:\"109\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"湖北\",v:\"28\",opt_data_array:[{t:\"湖北一区\",v:\"9\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖北二区\",v:\"24\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖北三区\",v:\"48\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖北四区\",v:\"68\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖北五区\",v:\"76\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖北六区\",v:\"94\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖北七区\",v:\"115\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"湖北八区\",v:\"127\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"华北\",v:\"29\",opt_data_array:[{t:\"华北一区\",v:\"10\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"华北二区\",v:\"19\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"华北三区\",v:\"54\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"华北四区\",v:\"87\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"西北\",v:\"31\",opt_data_array:[{t:\"西北一区\",v:\"12\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"西北2/3区\",v:\"46\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"东北\",v:\"32\",opt_data_array:[{t:\"东北一区\",v:\"13\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"东北二区\",v:\"18\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"东北3/7区\",v:\"23\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"东北4/5/6区\",v:\"83\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"西南\",v:\"34\",opt_data_array:[{t:\"西南一区\",v:\"17\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"西南二区\",v:\"49\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"西南三区\",v:\"92\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"河南\",v:\"35\",opt_data_array:[{t:\"河南一区\",v:\"27\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河南二区\",v:\"43\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河南三区\",v:\"57\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河南四区\",v:\"69\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河南五区\",v:\"77\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河南六区\",v:\"103\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河南七区\",v:\"135\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"广西\",v:\"36\",opt_data_array:[{t:\"广西一区\",v:\"28\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广西2/4区\",v:\"64\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广西三区\",v:\"88\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"广西五区\",v:\"133\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"江西\",v:\"37\",opt_data_array:[{t:\"江西一区\",v:\"29\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江西二区\",v:\"62\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"江西三区\",v:\"96\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"安徽\",v:\"38\",opt_data_array:[{t:\"安徽一区\",v:\"30\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"安徽二区\",v:\"58\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"安徽三区\",v:\"104\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"辽宁\",v:\"39\",opt_data_array:[{t:\"辽宁一区\",v:\"31\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"辽宁二区\",v:\"47\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"辽宁三区\",v:\"61\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"山西\",v:\"40\",opt_data_array:[{t:\"山西一区\",v:\"32\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"山西二区\",v:\"95\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"陕西\",v:\"41\",opt_data_array:[{t:\"陕西一区\",v:\"33\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"陕西2/3区\",v:\"63\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"广州\",v:\"42\",opt_data_array:[{t:\"广州1/2区\",v:\"34\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"河北\",v:\"43\",opt_data_array:[{t:\"河北一区\",v:\"38\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河北2/3区\",v:\"67\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河北四区\",v:\"118\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"河北五区\",v:\"132\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"重庆\",v:\"44\",opt_data_array:[{t:\"重庆一区\",v:\"39\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"重庆二区\",v:\"73\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"黑龙江\",v:\"45\",opt_data_array:[{t:\"黑龙江一区\",v:\"40\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"黑龙江2/3区\",v:\"51\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"吉林\",v:\"46\",opt_data_array:[{t:\"吉林1/2区\",v:\"42\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"云贵\",v:\"277\",opt_data_array:[{t:\"云南一区\",v:\"120\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"贵州一区\",v:\"122\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"云贵一区\",v:\"124\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"天津\",v:\"278\",opt_data_array:[{t:\"天津一区\",v:\"121\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"新疆\",v:\"281\",opt_data_array:[{t:\"新疆一区\",v:\"123\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"内蒙\",v:\"538\",opt_data_array:[{t:\"内蒙古一区\",v:\"125\",status:\"1\",display:\"1\",opt_data_array:[]}]},{t:\"体验服\",v:\"7016761\",opt_data_array:[{t:\"体验一区\",v:\"99\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"体验二区\",v:\"199\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"体验5区\",v:\"205\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"体验6区\",v:\"206\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"体验三区\",v:\"200\",status:\"1\",display:\"1\",opt_data_array:[]},{t:\"体验四区\",v:\"208\",status:\"1\",display:\"1\",opt_data_array:[]}]}]");

    /**
     * 角色列表
     */
    public static List<String> characterList = null;

    /**
     * 角色Map
     */
    public static Map<String, Character> characterMap = null;

    /**
     * 已选角色
     */
    public static Integer areaId = null;

    /**
     * 已选角色
     */
    public static Character character = null;

    /**
     * 中文正则表达式
     */
    private static Pattern CN_PATTERN = Pattern.compile("[\u4E00-\u9FA5]");

    /**
     * 获取大区
     *
     * @param
     * @return void
     * @author XanderYe
     * @date 2020/4/1
     */
    public static void getArea() {
        String url = "https://game.qq.com/comm-htdocs/js/game_area/dnf_server_select.js";
        byte[] data = HttpUtil.doDownload(url, null, null, null);
        try {
            String result = new String(data, "GBK");
            String json = StringUtils.substringBetween(result, "DNFServerSelect.STD_DATA= ", ";");
            if (json != null) {
                areaArray = JSON.parseArray(json);
            }
        } catch (Exception e) {
            logger.error("msg", e);
        }
    }

    /**
     * 获取角色
     *
     * @param area
     * @return void
     * @author XanderYe
     * @date 2020/4/1
     */
    public static void getCharacterList(int area) throws Exception {
        String url = "https://comm.aci.game.qq.com/main?game=dnf&area=" + area + "&callback=" + QQUtil.getCallback() + "&sCloudApiName=ams.gameattr.role&iAmsActivityId=https%3A%2F%2Fdnf.qq.com%2Fgift.shtml&sServiceDepartment=group_3&r=1";
        Map<String, Object> headers = new HashMap<>();
        headers.put("Referer", "https://dnf.qq.com/gift.shtml");
        String result = HttpUtil.doGet(url, headers, cookies, null);
        result = URLDecoder.decode(result, "UTF-8");
        result = StringUtils.substringBetween(result, "data:'", "&_webplat_msg_code");
        String[] roles = result.split("[|]");
        characterMap = new HashMap<>(16);
        characterList = new ArrayList<>();
        if (roles.length > 1) {
            for (int i = 1; i < roles.length; i++) {
                String[] tmp = roles[i].split(" ");
                Character character = new Character();
                character.setCharacterNo(tmp[0]);
                character.setCharacterName(tmp[1]);
                character.setCharacterOrder(Integer.valueOf(tmp[2]));
                characterMap.put(character.getCharacterName(), character);
                characterList.add(tmp[1]);
            }
        }
    }

    /**
     * 领奖励方法
     *
     * @param payload
     * @return void
     * @author XanderYe
     * @date 2020/4/1
     */
    public static String get(Payload payload) throws Exception {
        String headerString = payload.getHeaders();
        String paramString = payload.getParams();
        String url = replaceUrl(payload.getInterfaceUrl(), paramString);
        if (paramString != null) {
            paramString = replaceParam(paramString);
        }
        Map<String, Object> headers = HttpUtil.formatHeaders(headerString);
        Map<String, Object> params = HttpUtil.formatParameters(paramString);
        String result;
        if (payload.getMethod() == 0) {
            result = HttpUtil.doGet(url, headers, cookies, params);
        } else {
            result = HttpUtil.doPost(url, headers, cookies, params);
        }
        result = UnicodeUtil.unicodeStrToString(result);
        List<String> cn = new ArrayList<>();
        try {
            jsonObjIt(JSON.parseObject(result), cn);
        } catch (Exception e) {
            logger.error("msg", e);
        }
        return cn.size() > 0 ? String.join(" ", cn) : result;
    }

    public static String replaceUrl(String url, String paramString) {
        try {
            if (url.contains(Constant.S_MILO_TAG)) {
                String iActivityId = StringUtils.substringBetween(paramString, "iActivityId=", "&");
                String iFlowId = StringUtils.substringBetween(paramString, "iFlowId=", "&");
                String openId = (String) cookies.get("openid");
                url = url.replace(Constant.S_MILO_TAG, QQUtil.sMiloTag(iActivityId, iFlowId, openId));
            }
            url = url.replace(Constant.RANDOM, String.valueOf(System.currentTimeMillis()));
        } catch (Exception e) {
            logger.error("msg", e);
        }
        return url;
    }

    public static String replaceParam(String string) {
        String skey = (String) cookies.get("skey");
        try {
            string = string.replace(Constant.RANDOM, String.valueOf(System.currentTimeMillis()))
                    .replace(Constant.AREA_ID, String.valueOf(areaId))
                    .replace(Constant.CHARACTER_NO, character.getCharacterNo())
                    .replace(Constant.CHARACTER_NAME, character.getCharacterName())
                    .replace(Constant.GTK, QQUtil.getGTK(skey))
                    .replace(Constant.UUID, UUID.randomUUID().toString())
                    .replace(Constant.SKEY, skey);
        } catch (Exception e) {
            logger.error("msg", e);
        }
        return string;
    }

    /**
     * 递归遍历jsonObject
     * @param jsonObject
     * @param list
     * @return void
     * @author XanderYe
     * @date 2020/4/7
     */
    private static void jsonObjIt(JSONObject jsonObject, List list) {
        for(JSONObject.Entry<String, Object> entry : jsonObject.entrySet()) {
            String value = entry.getValue().toString();
            if (value.startsWith("[{")) {
                jsonArrayIt(JSON.parseArray(value), list);
            } else if (value.startsWith("{")) {
                jsonObjIt(JSON.parseObject(value), list);
            } else {
                Matcher matcher = CN_PATTERN.matcher(value);
                if (matcher.find()) {
                    list.add(value);
                }
            }
        }
    }

    /**
     * 递归遍历jsonArray
     * @param jsonArray
     * @param list
     * @return void
     * @author XanderYe
     * @date 2020/4/7
     */
    private static void jsonArrayIt(JSONArray jsonArray, List list) {
        for (int i=0;i<jsonArray.size();i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObjIt(jsonObject, list);
        }
    }

    public static void main(String[] args) {
        Payload payload = new Payload();
        payload.setMethod(1);
        payload.setInterfaceUrl("http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true");
        //payload.setHeaders("Referer:https://guanjia.qq.com/act/cop/202003dnf/?ADTAG=cop.dnfxsy.youxi.banner");
        payload.setParams("iActivityId=166962&iFlowId=512402&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=${random}&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_15865063727223");
        String cookieString = "pgv_pvi=5830253568; pgv_pvid=4927550240; RK=vRzwD9jHcy; ptcz=681f21135416a87f3e878f32007caf22d63891dc803a5369bd134e108ebb8fca; PTTosSysFirstTime=1584576000000; PTTuserFirstTime=1584576000000; PTTosFirstTime=1584576000000; eas_sid=Q1I5H8R4S5x938X8C8s3345916; tvfe_boss_uuid=51e9ee43199403dc; XWINDEXGREY=0; sd_userid=48731585894669276; sd_cookie_crttime=1585894669276; isHostDate=18359; isOsSysDate=18359; isOsDate=18359; weekloop=12-0-14-15; o_cookie=315695355; pac_uid=1_315695355; _qpsvr_localtk=0.31929163310470354; pgv_si=s6881472512; pgv_info=ssid=s9183411091&pgvReferrer=; eas_entry=https%3A%2F%2Ftool.helper.qq.com%2Fv3%2Fdnf%2Fofficial_website%2Findex.shtml; ied_rf=tool.helper.qq.com/v3/dnf/official_website/index.shtml; midas_openkey=@UqCMPr2Bd; midas_openid=315695355; xinyueqqcomrouteLine=a20181101rights_a20181101rights_a20181101rights_a20181101rights_a20181101rights_a20181101rights_a20181101rights; d7a9c0c275a4b8c94cf7397cc1d8bcc8=315695355; ts_last=dnf.qq.com/gift.shtml; gpmtips_cfg=%7B%22iSendApi%22%3A0%2C%22iShowCount%22%3A0%2C%22iOnlineCount%22%3A2%2C%22iSendOneCount%22%3A0%2C%22iShowAllCount%22%3A0%2C%22iHomeCount%22%3A0%7D; verifysession=h018d323e165a48ef49e1563f4f0207271c54a5a85ecf8a967345bda7316d825bcc14241092781261fb; recommend_init=1; rec_req_ctips_dnf=1; ptui_loginuin=202451454; uin=o0202451454; skey=@82GwbgNZm";
        cookies = HttpUtil.formatCookies(cookieString);
        areaId = 11;
        character = new Character();
        character.setCharacterNo("47807962");
        character.setCharacterName("大狗又空辣");
        try {
            String result = get(payload);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
