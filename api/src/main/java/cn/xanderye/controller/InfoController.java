package cn.xanderye.controller;

import cn.xanderye.base.ResultBean;
import cn.xanderye.entity.Activity;
import cn.xanderye.entity.Announcement;
import cn.xanderye.entity.Log;
import cn.xanderye.entity.Version;
import cn.xanderye.mapper.ActivityMapper;
import cn.xanderye.mapper.AnnouncementMapper;
import cn.xanderye.mapper.LogMapper;
import cn.xanderye.mapper.VersionMapper;
import cn.xanderye.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("info")
public class InfoController {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private VersionMapper versionMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private AnnouncementMapper announcementMapper;

    @GetMapping("getActivities")
    public ResultBean getActivities() {
        List<Activity> activityList = activityMapper.getActivities();
        return new ResultBean<>(activityList);
    }

    @GetMapping("getActivated")
    public ResultBean getActivated() {
        List<Activity> activityList = activityMapper.getActivated();
        return new ResultBean<>(activityList);
    }

    @GetMapping("version")
    public Version getLaseVersion() {
        return versionMapper.getLastVersion();
    }

    @PostMapping("log")
    public ResultBean log(Log log, HttpServletRequest request) {
        if (log.getCharacter() == null) {
            throw new RuntimeException("参数不为空");
        }
        log.setIp(RequestUtil.getIpAddress(request));
        log.setTime(new Date());
        logMapper.insert(log);
        return new ResultBean();
    }

    @GetMapping("time")
    public ResultBean time() {
        return new ResultBean(System.currentTimeMillis());
    }

    @GetMapping("announcement")
    public ResultBean announcement() {
        Announcement announcement = announcementMapper.getLatest();
        String content = announcement != null ? announcement.getContent() : "";
        return new ResultBean<>(content);
    }
}
