package cn.xanderye.schedule;

import cn.xanderye.entity.Activity;
import cn.xanderye.mapper.ActivityMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created on 2020/4/22.
 *
 * @author 叶振东
 */
@Slf4j
@Configuration
public class ExpirationCheckSchedule {
    @Autowired
    private ActivityMapper activityMapper;

    @Scheduled(cron = "0 0 5 * * ? *")
    public void check() {
        log.info("开始清理过期活动");
        List<Activity> activityList = activityMapper.getActivities();
        for (Activity activity:activityList) {
            if (activity.getExpirationTime() != null && activity.getExpirationTime().getTime() > System.currentTimeMillis()) {
                activityMapper.expire(activity.getId());
            }
        }
    }
}
