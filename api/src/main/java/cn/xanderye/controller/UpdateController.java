package cn.xanderye.controller;

import cn.xanderye.base.ResultBean;
import cn.xanderye.constant.Constant;
import cn.xanderye.entity.Activity;
import cn.xanderye.entity.Announcement;
import cn.xanderye.entity.Payload;
import cn.xanderye.entity.Version;
import cn.xanderye.mapper.ActivityMapper;
import cn.xanderye.mapper.AnnouncementMapper;
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
    @Autowired
    private AnnouncementMapper announcementMapper;

    @Value("${update.root}")
    private String uploadRoot;

    @PostMapping("activity")
    public ResultBean insertActivity(@RequestBody Activity activity) {
        activity.setExpired(false);
        activityMapper.insert(activity);
        List<Payload> payloadList = activity.getPayloadList();
        if (payloadList != null && payloadList.size() > 0) {
            for (Payload payload : payloadList) {
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
        String url = payload.getInterfaceUrl();
        String params = payload.getParams();
        payload.setInterfaceUrl(replaceString(url));
        payload.setParams(replaceString(params));
        payloadMapper.insert(payload);
        return new ResultBean();
    }

    @PostMapping("version")
    public ResultBean insertVersion(Version version, @RequestParam("file") MultipartFile file) {
        version.setDate(new Date());
        versionMapper.insert(version);
        String[] fileNameSplit = file.getOriginalFilename().split("[.]");
        String fileName = "onestep." + fileNameSplit[fileNameSplit.length - 1];
        String dest = uploadRoot + File.separator + fileName;
        try {
            file.transferTo(new File(dest));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultBean();
    }

    @PostMapping("announcement")
    public ResultBean announcement(@RequestBody Announcement announcement) {
        announcement.setTime(new Date());
        announcementMapper.insert(announcement);
        return new ResultBean();
    }

    /**
     * 替换字符
     * @param s
     * @return java.lang.String
     * @author XanderYe
     * @date 2020/4/7
     */
    private String replaceString(String s) {
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

    private StringBuilder replace(StringBuilder sb, String key, String replaceString) {
        return replace(sb, key, replaceString, null);
    }

    private StringBuilder replace(StringBuilder sb, String key, String replaceString, String endString) {
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
