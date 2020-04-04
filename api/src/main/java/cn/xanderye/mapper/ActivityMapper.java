package cn.xanderye.mapper;

import cn.xanderye.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created on 2020/4/2.
 *
 * @author XanderYe
 */
@Mapper
public interface ActivityMapper {

    /**
     * 获取所有活动
     * @param
     * @return java.util.List<cn.xanderye.onestep.entity.Activity>
     * @author XanderYe
     * @date 2020/4/4
     */
    List<Activity> getActivities();

    /**
     * 获取当前活动
     * @param
     * @return java.util.List<cn.xanderye.onestep.entity.Activity>
     * @author XanderYe
     * @date 2020/4/2
     */
    List<Activity> getActivated();

    /**
     * 获取所有活动
     * @param
     * @return java.util.List<cn.xanderye.onestep.entity.Activity>
     * @author XanderYe
     * @date 2020/4/2
     */
    List<Activity> getAll();

    /**
     * 添加活动
     * @param activity
     * @return int
     * @author XanderYe
     * @date 2020-04-04
     */
    int insert(Activity activity);

    /**
     * 设置活动过期
     * @param activityId
     * @return void
     * @author XanderYe
     * @date 2020-04-04
     */
    void expire(Integer activityId);
}
