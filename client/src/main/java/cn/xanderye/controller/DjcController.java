package cn.xanderye.controller;

import cn.xanderye.entity.Payload;
import cn.xanderye.util.DNFUtil;
import cn.xanderye.util.DjcUtil;
import cn.xanderye.util.PropertyUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created on 2020/9/7.
 *
 * @author 叶振东
 */
public class DjcController implements Initializable {

    private static Logger logger = LoggerFactory.getLogger(DjcController.class);

    Map<String, String> iGoodMap = new HashMap<String, String>(){{
        put("调整箱5个", "753");
        put("疲劳药10点", "755");
    }};

    @FXML
    private AnchorPane root;

    @FXML
    private ComboBox iGoodBox;

    private TextArea logArea;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logArea = MainController.log;
        ObservableList<String> iGoodIds = FXCollections.observableArrayList(iGoodMap.keySet().toArray(new String[0]));
        iGoodBox.setItems(iGoodIds);
        String iGoodName = PropertyUtil.get("iGoodName");
        if (StringUtils.isEmpty(iGoodName)) {
            iGoodBox.getSelectionModel().selectFirst();
        } else {
            iGoodBox.setValue(iGoodName);
        }
    }

    public void djc() {
        Stage stage = (Stage) root.getScene().getWindow();
        stage.close();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            String goodName = (String) iGoodBox.getValue();
            String iGoodId = iGoodMap.get(goodName);
            Map<String, Object> cookieMap = DNFUtil.cookies;
            cookieMap.put("djc_appSource", "android");
            cookieMap.put("djc_appVersion", "102");
            cookieMap.put("acctype", "");
            List<Payload> payloadList = new ArrayList<>();
            Payload payload;

            try {
                String result = DjcUtil.getDemandList();
                JSONObject resultObject = JSON.parseObject(result);
                JSONObject data = resultObject.getJSONObject("data");
                JSONArray list = data.getJSONArray("list");
                if (list.size() > 0) {
                    JSONObject jsonObject = list.getJSONObject(0);

                    String iZoneId = jsonObject.getString("iZoneId");
                    String sRoleName = jsonObject.getString("sRoleName");
                    String sZoneDesc = URLEncoder.encode(jsonObject.getString("sZoneDesc"), "UTF-8");
                    String sBizCode = jsonObject.getString("sBizCode");
                    String sGoodId = "";
                    payload = new Payload();
                    payload.setInterfaceUrl("https://djcapp.game.qq.com/daoju/igw/main/");
                    String paramString = "_service=app.demand.create&iAppId=1001&_app_id=1001&p_tk=${gTk}&iActionId=3&sGetterDream=%E5%9C%9F%E8%B1%AA%E5%9C%9F%E8%B1%AA%E6%B1%82%E5%8C%85%E5%85%BB%EF%BC%81&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-25&ch=10000&sVersionName=v4.1.2.1&appSource=android&sDjcSign=${djcSign}";
                    paramString += "&sBizCode=" + sBizCode +
                            "&iZoneId=" + iZoneId +
                            "&sRoleName=" + sRoleName;
                    if ("cf".equals(sBizCode)) {
                        sGoodId = "2395";
                        paramString += "&sZoneDesc= " + sZoneDesc;
                        payload.setNote("许愿猎狐者");
                    } else if ("lol".equals(sBizCode)) {
                        sGoodId = "674";
                        payload.setNote("许愿德玛西亚之力盖伦");
                    }
                    paramString +=  "&iGoodsId=" + sGoodId + "&sRoleId=${qq}";
                    payload.setParams(paramString);
                    payload.setMethod(0);
                    String res = DNFUtil.get(payload);
                    Payload finalPayload = payload;
                    Platform.runLater(() -> {
                        logArea.appendText(finalPayload.getNote() + "：" + res + "\n");
                    });
                } else {
                    Platform.runLater(() -> {
                        logArea.appendText("请先许愿一个除了猎狐者/德玛西亚之力盖伦以外的道具\n");
                    });
                }
            } catch (Exception e) {
                Platform.runLater(() -> {
                    logArea.appendText("获取许愿列表失败\n");
                });
            }

            // 签到
            payload = new Payload();
            payload.setInterfaceUrl("https://comm.ams.game.qq.com/ams/ame/amesvr?ameVersion=0.3&sServiceType=dj&iActivityId=11117&sServiceDepartment=djc&set_info=newterminals&&weexVersion=0.9.4&platform=android&deviceModel=${deviceModel}&&appSource=android&appVersion=102&ch=10000&sDeviceID=${deviceId}&osVersion=Android-22&p_tk=${gTk}&sVersionName=v4.1.2.1");
            payload.setParams("appVersion=102&ch=10000&iActivityId=11117&sDjcSign=${djcSign}&sDeviceID=${deviceId}&p_tk=${gTk}&osVersion=Android-22&iFlowId=96939&sVersionName=v4.1.2.1&sServiceDepartment=djc&sServiceType=dj&appSource=android&g_tk=${gTk}");
            payload.setMethod(1);
            payload.setTimes(1);
            payload.setTimeout(1);
            payload.setNote("签到");
            payloadList.add(payload);

            // 浏览绝不错亿
            payload = new Payload();
            payload.setInterfaceUrl("https://djcapp.game.qq.com/daoju/igw/main/");
            payload.setParams("_service=app.task.report&&weexVersion=0.9.4&platform=android&deviceModel=${deviceModel}&task_type=activity_center&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-22&ch=10000&sVersionName=v4.1.2.1&appSource=android&sDjcSign=${djcSign}");
            payload.setMethod(0);
            payload.setNote("浏览绝不错亿");
            payloadList.add(payload);

            // 绝不错亿
            payload = new Payload();
            payload.setInterfaceUrl("https://apps.game.qq.com/daoju/v3/api/we/usertaskv2/Usertask.php");
            payload.setParams("iAppId=1001&_app_id=1001&p_tk=${gTk}&output_format=json&_output_fmt=json&optype=receive_usertask&appid=1001&iruleId=100040&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-22&ch=10000&sVersionName=v4.1.2.1&appSource=android");
            payload.setMethod(0);
            payload.setNote("绝不错亿");
            payloadList.add(payload);

            // 兑换道具
            payload = new Payload();
            payload.setInterfaceUrl("https://apps.game.qq.com/cgi-bin/daoju/v3/hs/i_buy.cgi");
            payload.setParams("weexVersion=0.9.4&platform=android&deviceModel=${deviceModel}&&&_output_fmt=1&_plug_id=9800&_from=app&iGoodsSeqId=" + iGoodId + "&iActionId=2594&iActionType=26&_biz_code=dnf&biz=dnf&appid=1003&_app_id=1003&rolename=${characterName}&lRoleId=${characterNo}&iZone=${areaId}&p_tk=${gTk}&_cs=2&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-22&ch=10000&sVersionName=v4.1.2.1&appSource=android&sDjcSign=${djcSign}");
            payload.setMethod(0);
            payload.setNote("兑换" + goodName);
            payloadList.add(payload);

            // 兑换有礼
            payload = new Payload();
            payload.setInterfaceUrl("https://apps.game.qq.com/daoju/v3/api/we/usertaskv2/Usertask.php");
            payload.setParams("iAppId=1001&_app_id=1001&p_tk=${gTk}&output_format=json&_output_fmt=json&optype=receive_usertask&appid=1001&iruleId=327091&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-22&ch=10000&sVersionName=v4.1.2.1&appSource=android");
            payload.setMethod(0);
            payload.setNote("兑换有礼");
            payloadList.add(payload);

            // 有理想
            payload = new Payload();
            payload.setInterfaceUrl("https://apps.game.qq.com/daoju/v3/api/we/usertaskv2/Usertask.php");
            payload.setParams("iAppId=1001&_app_id=1001&p_tk=${gTk}&output_format=json&_output_fmt=json&optype=receive_usertask&appid=1001&iruleId=302124&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-22&ch=10000&sVersionName=v4.1.2.1&appSource=android");
            payload.setMethod(0);
            payload.setNote("有理想");
            payloadList.add(payload);

            // 银宝箱
            payload = new Payload();
            payload.setInterfaceUrl("https://apps.game.qq.com/daoju/v3/api/we/usertaskv2/Usertask.php");
            payload.setParams("iAppId=1001&_app_id=1001&p_tk=${gTk}&output_format=json&_output_fmt=json&optype=receive_usertask&appid=1001&iruleId=100001&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-22&ch=10000&sVersionName=v4.1.2.1&appSource=android");
            payload.setMethod(0);
            payload.setNote("银宝箱");
            payloadList.add(payload);

            // 金宝箱
            payload = new Payload();
            payload.setInterfaceUrl("https://apps.game.qq.com/daoju/v3/api/we/usertaskv2/Usertask.php");
            payload.setParams("iAppId=1001&_app_id=1001&p_tk=${gTk}&output_format=json&_output_fmt=json&optype=receive_usertask&appid=1001&iruleId=100002&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-22&ch=10000&sVersionName=v4.1.2.1&appSource=android");
            payload.setMethod(0);
            payload.setNote("金宝箱");
            payloadList.add(payload);

            // 执行次数
            for (Payload p : payloadList) {
                try {
                    String result = DNFUtil.get(p);
                    if (StringUtils.isNoneEmpty(p.getNote())) {
                        Platform.runLater(() -> {
                            logArea.appendText(p.getNote() + "：" + result + "\n");
                        });
                    }
                    Thread.sleep(1000);
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        logArea.appendText("接口访问失败\n");
                    });
                }
            }

            try {
                String result = DjcUtil.getDemandList();
                JSONObject resultObject = JSON.parseObject(result);
                JSONObject data = resultObject.getJSONObject("data");
                JSONArray list = data.getJSONArray("list");
                JSONObject target = null;
                for (int i = 0; i < list.size(); i++) {
                    JSONObject jsonObject = list.getJSONObject(i);
                    String goodId = jsonObject.getString("iGoodsId");
                    if ("2395".equals(goodId) || "674".equals(goodId)) {
                        target = jsonObject;
                        break;
                    }
                }
                if (target != null) {
                    String sKeyId = target.getString("sKeyId");
                    payload = new Payload();
                    payload.setInterfaceUrl("https://apps.game.qq.com/daoju/djcapp/v5/demand/DemandDelete.php");
                    payload.setParams("output_format=jsonp&iAppId=1001&_app_id=1001&p_tk=${gTk}&output_format=json&_output_fmt=json" +
                            "&sKeyId=" + sKeyId + "&sDeviceID=${deviceId}&appVersion=102&p_tk=${gTk}&osVersion=Android-25&ch=10000&sVersionName=v4.1.2.1&appSource=android");
                    payload.setMethod(0);
                    payload.setNote("删除许愿");
                    String res = DNFUtil.get(payload);
                    Payload finalPayload = payload;
                    Platform.runLater(() -> {
                        logArea.appendText(finalPayload.getNote() + "：" + res + "\n");
                    });
                } else {
                    Platform.runLater(() -> {
                        logArea.appendText("未找到许愿道具，无法删除\n");
                    });
                }
            } catch (Exception e) {
                logger.error("msg", e);
                Platform.runLater(() -> {
                    logArea.appendText("获取许愿列表失败\n");
                });
            }
            PropertyUtil.save("iGoodName", goodName);
        });
        executorService.shutdown();
    }
}
