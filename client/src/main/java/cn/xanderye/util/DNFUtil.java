package cn.xanderye.util;

import cn.xanderye.constant.Constant;
import cn.xanderye.entity.Character;
import cn.xanderye.entity.Payload;
import cn.xanderye.entity.User;
import cn.xanderye.license.License;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
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
    public static User user = new User();

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
        String checkParam = StringUtils.substringBetween(result, "checkparam:'", "',md5str");
        String md5str = StringUtils.substringBetween(result, "md5str:'", "',infostr");
        user.setCheckParam(checkParam);
        user.setMd5Str(md5str);
        result = StringUtils.substringBetween(result, "data:'", "&_webplat_msg_code");
        result = URLDecoder.decode(result, "UTF-8");
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
     * 获取心悦勇士币和成就点
     *
     * @param
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/4/14
     */
    public static String xinYueBind() {
        Payload payload = new Payload();
        payload.setMethod(1);
        payload.setInterfaceUrl("http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=dnf&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true");
        payload.setParams("sServiceType=dnf&user_area=${areaId}&user_roleId=${characterNo}&user_roleName=${userRoleId}&user_areaName=${userAreaName}&user_roleLevel=100&user_checkparam=${checkParam}&user_md5str=${md5Str}&user_sex=&user_platId=&user_partition=11&iActivityId=166962&iFlowId=512491&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&xhrPostKey=xhr_${random}");
        try {
            String result = get(payload);
            String[] tmp = result.split(" ");
            if (tmp.length == 3) {
                return tmp[2] + " " + tmp[1];
            } else {
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "角色绑定失败";
    }


    /**
     * 获取心悦勇士币和成就点
     *
     * @param
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/4/14
     */
    public static String getXinYuePoints() {
        Payload payload = new Payload();
        payload.setInterfaceUrl("http://act.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=tgclub&iActivityId=166962&sServiceDepartment=xinyue&sSDID=26ebd6b381f853ff7ecc1def1a43de7a&sMiloTag=${sMiloTag}&isXhrPost=true");
        payload.setParams("iActivityId=166962&iFlowId=512411&g_tk=${gTk}&e_code=0&g_code=0&eas_url=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F&eas_refer=http%3A%2F%2Fxinyue.qq.com%2Fact%2Fa20181101rights%2F%3Freqid%3D${uuid}%26version%3D22&xhr=1&sServiceDepartment=xinyue&sServiceType=tgclub&xhrPostKey=xhr_${random}");
        payload.setMethod(1);
        String data = "勇士币：{0}, 成就点：{1}";
        String coin = "0";
        String point = "0";
        try {
            String result = get(payload);
            JSONObject res = JSON.parseObject(result);
            JSONObject modRet = res.getJSONObject("modRet");
            coin = modRet.getString("sOutValue2");
            point = modRet.getString("sOutValue1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data.replace("{0}", coin).replace("{1}", point);
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
        result = UrlUtil.decode(result);
        List<String> cn = new ArrayList<>();
        try {
            jsonObjIt(JSON.parseObject(result), cn);
        } catch (Exception e) {
            logger.error("msg", e);
        }
        return cn.size() > 0 ? String.join(" ", cn) : result;
    }

    /**
     * 日志
     *
     * @param
     * @return void
     * @date 2020/4/14
     */
    public static void log() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("area", user.getArea());
            params.put("qq", user.getQq());
            params.put("character", user.getCharacterName());
            params.put("version", Constant.VERSION);
            String authDate = "未授权";
            if (License.licenseJson != null) {
                Long expireDate = License.licenseJson.getLong("expireDate");
                if (License.systemTime > expireDate) {
                    authDate = "已过期";
                } else {
                    Date date = new Date(expireDate);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    authDate = sdf.format(date);
                }

            }
            params.put("authDate", authDate);
            HttpUtil.doPost(Constant.LOG_URL, params);
        } catch (Exception ignored) {
        }
    }

    private static String replaceUrl(String url, String paramString) {
        try {
            if (url.contains(Constant.S_MILO_TAG)) {
                String iActivityId = StringUtils.substringBetween(paramString, "iActivityId=", "&").trim();
                String iFlowId = StringUtils.substringBetween(paramString, "iFlowId=", "&").trim();
                String id = user.getOpenId();
                if (id == null) {
                    id = user.getUin();
                }
                url = url.replace(Constant.S_MILO_TAG, QQUtil.sMiloTag(iActivityId, iFlowId, id));
            }
            if (url.contains(Constant.RANDOM)) {
                url = url.replace(Constant.RANDOM, String.valueOf(System.currentTimeMillis()));
            }
            if (url.contains(Constant.GTK)) {
                url = url.replace(Constant.GTK, user.getGTk());;
            }
            if (url.contains(Constant.DEVICE_ID)) {
                url = url.replace(Constant.DEVICE_ID, DNFUtil.user.getDeviceId());
            }
            if (url.contains(Constant.DEVICE_MODEL)) {
                url = url.replace(Constant.DEVICE_MODEL, DNFUtil.user.getDeviceModel());
            }
        } catch (Exception e) {
            logger.error("msg", e);
        }
        return url;
    }

    private static String replaceParam(String string) {
        try {
            if (string.contains(Constant.RANDOM)) {
                string = string.replace(Constant.RANDOM, String.valueOf(System.currentTimeMillis()));
            }
            if (string.contains(Constant.AREA)) {
                string = string.replace(Constant.AREA, user.getArea());
            }
            if (string.contains(Constant.USER_AREA_NAME)) {
                string = string.replace(Constant.USER_AREA_NAME, UrlUtil.doubleEncode(user.getArea()));
            }
            if (string.contains(Constant.AREA_ID)) {
                string = string.replace(Constant.AREA_ID, String.valueOf(user.getAreaId()));
            }
            if (string.contains(Constant.CHARACTER_NO)) {
                string = string.replace(Constant.CHARACTER_NO, user.getCharacterNo() == null ? "" : user.getCharacterNo());
            }
            if (string.contains(Constant.CHARACTER_NAME)) {
                string = string.replace(Constant.CHARACTER_NAME, user.getCharacterName() == null ? "" : user.getCharacterName());
            }
            if (string.contains(Constant.USER_ROLE_ID)) {
                string = string.replace(Constant.USER_ROLE_ID, user.getCharacterName() == null ? "" : UrlUtil.doubleEncode(user.getCharacterName()));
            }
            if (string.contains(Constant.GTK)) {
                string = string.replace(Constant.GTK, user.getGTk());
            }
            if (string.contains(Constant.UUID)) {
                string = string.replace(Constant.UUID, UUID.randomUUID().toString());
            }
            if (string.contains(Constant.CHECK_PARAM)) {
                string = string.replace(Constant.CHECK_PARAM, UrlUtil.encode(user.getCheckParam()));
            }
            if (string.contains(Constant.MD5_STR)) {
                string = string.replace(Constant.MD5_STR, user.getMd5Str());
            }
            if (string.contains(Constant.QQ)) {
                string = string.replace(Constant.QQ, user.getQq());
            }
            if (string.contains(Constant.SKEY)) {
                string = string.replace(Constant.SKEY, user.getSkey());
            }
            if (string.contains(Constant.DJC_SIGN)) {
                String sign = DjcUtil.djcSign(DNFUtil.user.getUin(), DNFUtil.user.getDeviceId());
                string = string.replace(Constant.DJC_SIGN, sign);
            }
            if (string.contains(Constant.DEVICE_ID)) {
                string = string.replace(Constant.DEVICE_ID, DNFUtil.user.getDeviceId());
            }
            if (string.contains(Constant.DEVICE_MODEL)) {
                string = string.replace(Constant.DEVICE_MODEL, DNFUtil.user.getDeviceModel());
            }
        } catch (Exception e) {
            logger.error("msg", e);
        }
        return string;
    }

    /**
     * 递归遍历jsonObject
     *
     * @param jsonObject
     * @param list
     * @return void
     * @author XanderYe
     * @date 2020/4/7
     */
    public static void jsonObjIt(JSONObject jsonObject, List list) {
        for (JSONObject.Entry<String, Object> entry : jsonObject.entrySet()) {
            String value = entry != null && entry.getValue() != null ? entry.getValue().toString() : "";
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
     *
     * @param jsonArray
     * @param list
     * @return void
     * @author XanderYe
     * @date 2020/4/7
     */
    private static void jsonArrayIt(JSONArray jsonArray, List list) {
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            jsonObjIt(jsonObject, list);
        }
    }

    /**
     * 设置角色信息
     *
     * @param character
     * @return void
     * @author XanderYe
     * @date 2020/4/14
     */
    public static void setUser(Character character) {
        user.setCharacterNo(character.getCharacterNo());
        user.setCharacterName(character.getCharacterName());
        user.setCharacterOrder(character.getCharacterOrder());
        user.setSkey((String) cookies.get("skey"));
        user.setGTk((QQUtil.getGTK(user.getSkey())));
        user.setUin((String) cookies.get("uin"));
        user.setQq(QQUtil.uinToQQ(user.getUin()));
        user.setOpenId((String) cookies.get("openid"));
        user.setQq(QQUtil.uinToQQ(user.getUin()));
    }
}
