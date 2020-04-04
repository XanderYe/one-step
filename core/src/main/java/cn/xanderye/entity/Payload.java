package cn.xanderye.entity;

import lombok.Data;

/**
 * Created on 2020/4/1.
 *
 * @author XanderYe
 */
@Data
public class Payload {
    private Integer id;

    private Integer activityId;

    private String interfaceUrl;

    private Integer method;

    private String headers;

    private String params;

    private String note;

    private Integer timeout;
}
