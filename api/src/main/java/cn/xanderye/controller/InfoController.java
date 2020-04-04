package cn.xanderye.controller;

import cn.xanderye.base.ResultBean;
import cn.xanderye.entity.Activity;
import cn.xanderye.entity.Version;
import cn.xanderye.mapper.ActivityMapper;
import cn.xanderye.mapper.VersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("info")
public class InfoController {
    @Autowired
    private ActivityMapper activityMapper;
    @Autowired
    private VersionMapper versionMapper;

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
}
