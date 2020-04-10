package cn.xanderye.controller;

import cn.xanderye.constant.Constant;
import cn.xanderye.entity.Activity;
import cn.xanderye.entity.Payload;
import cn.xanderye.entity.Version;
import cn.xanderye.util.DNFUtil;
import cn.xanderye.util.HttpUtil;
import cn.xanderye.util.PropertyUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created on 2020/4/1.
 *
 * @author XanderYe
 */
public class MainController implements Initializable {
    private static Logger logger = LoggerFactory.getLogger(MainController.class);
    @FXML
    private ComboBox areaBox;
    @FXML
    private ComboBox optBox;
    @FXML
    private ComboBox characterBox;
    @FXML
    private ListView activityListView;
    @FXML
    private TextArea logArea;
    @FXML
    private Button startButton;

    /**
     * 服务器数组 map
     */
    private Map<String, JSONArray> optMap = new HashMap<>(16);

    /**
     * 服务器和id map
     */
    private Map<String, Integer> areaIdMap = new HashMap<>(16);

    /**
     * 活动map
     */
    private Map<String, String> activityMap = new HashMap<>(16);

    /**
     * 活动列表
     */
    private List<Activity> activityList = null;

    public void start() {
        String characterName = (String) characterBox.getValue();
        if ("请选择角色".equals(characterName)) {
            logArea.appendText("请选择角色\n");
        } else {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                startButton.setDisable(true);
                DNFUtil.character = DNFUtil.characterMap.get(characterName);
                logArea.appendText("角色名称：" + DNFUtil.character.getCharacterName() + "\n");
                PropertyUtil.save("uin", (String) DNFUtil.cookies.get("uin"));
                PropertyUtil.save("area", (String) areaBox.getValue());
                PropertyUtil.save("opt", (String) optBox.getValue());
                PropertyUtil.save("characterName", characterName);
                if (activityList != null && activityList.size() > 0) {
                    int threadNumber = Math.min(activityList.size(), 10);
                    ExecutorService startService = Executors.newFixedThreadPool(threadNumber);
                    for (Activity activity : activityList) {
                        List<Payload> payloadList = activity.getPayloadList();
                        if (payloadList != null && payloadList.size() > 0) {
                            startService.execute(() -> {
                                for (Payload payload : payloadList) {
                                    if (payload.getTimes() == null) {
                                        payload.setTimes(1);
                                    }
                                    // 执行次数
                                    for (int i = 0; i < payload.getTimes(); i++) {
                                        try {
                                            String result = DNFUtil.get(payload);
                                            logArea.appendText(payload.getNote() + "：" + result + "\n");
                                            Integer timeout = payload.getTimeout();
                                            if (timeout == null) {
                                                timeout = 1;
                                            }
                                            Thread.sleep(timeout * 1000);
                                        } catch (Exception e) {
                                            logArea.appendText("未知错误\n");
                                            logger.error("msg", e);
                                        }
                                    }
                                }
                            });
                        }
                    }
                    startService.shutdown();
                    try {
                        startService.awaitTermination(10, TimeUnit.MINUTES);
                        startButton.setDisable(false);
                        logArea.appendText("执行完毕\n");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            executorService.shutdown();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化配置
        PropertyUtil.init(null);
        String uin = PropertyUtil.get("uin");
        String area = PropertyUtil.get("area");
        String opt = PropertyUtil.get("opt");
        String cname = PropertyUtil.get("characterName");

        List<String> areaList = new ArrayList<>();
        for (int i = 0; i < DNFUtil.areaArray.size(); i++) {
            JSONObject jsonObject = DNFUtil.areaArray.getJSONObject(i);
            String name = jsonObject.getString("t");
            JSONArray optArray = jsonObject.getJSONArray("opt_data_array");
            optMap.put(name, optArray);
            areaList.add(name);
        }
        ObservableList<String> areaOptions = FXCollections.observableArrayList(areaList.toArray(new String[0]));
        areaBox.setItems(areaOptions);

        areaBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (!"请选择大区".equals(newValue)) {
                JSONArray optArray = optMap.get(newValue);
                List<String> optList = new ArrayList<>();
                for (int i = 0; i < optArray.size(); i++) {
                    JSONObject jsonObject = optArray.getJSONObject(i);
                    String name = jsonObject.getString("t");
                    Integer value = jsonObject.getInteger("v");
                    areaIdMap.put(name, value);
                    optList.add(name);
                }
                ObservableList<String> optOptions = FXCollections.observableArrayList(optList.toArray(new String[0]));
                optBox.setItems(optOptions);
                optBox.setValue("请选择服务器");
                characterBox.setValue("请选择角色");
            }
        });

        optBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            List<String> characterNameList = new ArrayList<>();
            if (!"请选择服务器".equals(newValue)) {
                Integer areaId = areaIdMap.get(newValue);
                DNFUtil.areaId = areaId;
                try {
                    DNFUtil.getCharacterList(areaId);
                    if (DNFUtil.characterList != null) {
                        characterNameList.addAll(DNFUtil.characterList);
                    }
                } catch (Exception e) {
                    logger.error("msg", e);
                    logArea.appendText("未知错误");
                }
            }
            ObservableList<String> roleOptions2 = FXCollections.observableArrayList(characterNameList.toArray(new String[0]));
            characterBox.setItems(roleOptions2);
            characterBox.setValue("请选择角色");
        });

        // 登录了其他账号重置配置
        String nowUin = (String) DNFUtil.cookies.get("uin");
        if (area == null || nowUin == null || !nowUin.equals(uin)) {
            area = "请选择大区";
            opt = "请选择服务器";
            cname = "请选择角色";
        }
        areaBox.setValue(area);
        optBox.setValue(opt);
        characterBox.setValue(cname);


        try {
            // 获取数据
            String result = HttpUtil.doGet(Constant.ACTIVITY_URL, null);
            JSONObject resultJson = JSON.parseObject(result);
            JSONArray data = resultJson.getJSONArray("data");
            if (data != null) {
                activityList = JSONObject.parseArray(JSONObject.toJSONString(data), Activity.class);
                List<String> activityNameList = new ArrayList<>();
                for (Activity activity : activityList) {
                    activityMap.put(activity.getName(), activity.getUrl());
                    activityNameList.add(activity.getName());
                }
                ObservableList<String> list = FXCollections.observableArrayList(activityNameList.toArray(new String[0]));
                activityListView.setItems(list);
                activityListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    // 调用浏览器打开网页
                    try {
                        Runtime.getRuntime().exec("cmd /c start " + activityMap.get(newValue));
                    } catch (IOException e) {
                        logger.error("msg", e);
                    }
                });
            }
        } catch (Exception e) {
            logArea.appendText("获取活动信息错误\n");
            logger.error("msg", e);
        }

        // 检查更新
        ScheduledExecutorService updateService = Executors.newSingleThreadScheduledExecutor();
        updateService.schedule(() -> {
            checkUpdate();
            if (UpdateController.version != null) {
                if (!Constant.VERSION.equals(UpdateController.version.getVersion())) {
                    Platform.runLater(() -> {
                        try {
                            Stage stage = new Stage();
                            Parent root = FXMLLoader.load(getClass().getResource("/update.fxml"));
                            stage.setTitle("检查到更新");
                            Scene scene = new Scene(root, 400, 200);
                            stage.setScene(scene);
                            stage.setResizable(false);
                            stage.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
            updateService.shutdown();
        }, 3, TimeUnit.SECONDS);
    }

    public void close() {
        Platform.exit();
    }

    public void home() {
        // 调用浏览器打开网页
        try {
            Runtime.getRuntime().exec("cmd /c start https://www.xanderye.cn/");
        } catch (IOException e) {
            logger.error("msg", e);
        }
    }

    public void about() {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20, 30, 30, 30));
        Label label = new Label();
        label.setText("本程序免费使用\nbug提交：邮箱：315695355@qq.com，如有日志请上传日志");
        label.setFont(new Font(13));
        label.setWrapText(true);
        root.setCenter(label);
        stage.setTitle("关于");
        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void update() {
        checkUpdate();
        if (UpdateController.version != null) {
            if (!Constant.VERSION.equals(UpdateController.version.getVersion())) {
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("/update.fxml"));
                    stage.setTitle("检查到更新");
                    Scene scene = new Scene(root, 400, 200);
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                alert("无更新");
            }
        } else {
            alert("检查更新失败");
        }
    }

    /**
     * 检查更新
     *
     * @param
     * @return void
     * @author XanderYe
     * @date 2020/4/2
     */
    private void checkUpdate() {
        String result = HttpUtil.doGet(Constant.CHECK_URL, null);
        Version version = JSON.parseObject(result, Version.class);
        UpdateController.version = version;
    }

    public void alert(String msg) {
        Stage stage = new Stage();
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(20, 30, 30, 30));
        Label label = new Label();
        label.setText(msg);
        label.setFont(new Font(14));
        label.setWrapText(true);
        root.setCenter(label);
        stage.setTitle("更新");
        Scene scene = new Scene(root, 200, 100);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
