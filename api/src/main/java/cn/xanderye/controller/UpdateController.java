package cn.xanderye.controller;

import cn.xanderye.base.ResultBean;
import cn.xanderye.entity.Activity;
import cn.xanderye.entity.Payload;
import cn.xanderye.entity.Version;
import cn.xanderye.mapper.ActivityMapper;
import cn.xanderye.mapper.PayloadMapper;
import cn.xanderye.mapper.VersionMapper;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("save")
public class UpdateController {

    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private PayloadMapper payloadMapper;
    @Autowired
    private VersionMapper versionMapper;

    @Value("${update.root}")
    private String uploadRoot;

    @PostMapping("activity")
    public ResultBean insertActivity(@RequestBody Activity activity) {
        activity.setExpired(false);
        activityMapper.insert(activity);
        List<Payload> payloadList = activity.getPayloadList();
        if (payloadList != null && payloadList.size() > 0) {
            for(Payload payload:payloadList) {
                payloadMapper.insert(payload);
            }
        }
        return new ResultBean();
    }

    @PostMapping("expire")
    public ResultBean expire(@RequestBody JSONObject params) {
        Integer activityId = params.getInteger("activityId");
        activityMapper.expire(activityId);
        return new ResultBean();
    }

    @PostMapping("payload")
    public ResultBean insertPayload(@RequestBody Payload payload) {
        payloadMapper.insert(payload);
        return new ResultBean();
    }

    @PostMapping("version")
    public ResultBean insertVersion(Version version, @RequestParam("jar") MultipartFile jar) {
        version.setDate(new Date());
        versionMapper.insert(version);
        String fileName = "onestep.jar";
        String dest = uploadRoot + File.separator + fileName;
        try {
            jar.transferTo(new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean();
    }
}
