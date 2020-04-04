package cn.xanderye.mapper;

import cn.xanderye.entity.Payload;

public interface PayloadMapper {
    /**
     * 添加活动
     * @param payload
     * @return int
     * @author XanderYe
     * @date 2020-04-04
     */
    int insert(Payload payload);
}
