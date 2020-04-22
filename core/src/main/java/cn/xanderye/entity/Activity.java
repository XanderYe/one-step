package cn.xanderye.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created on 2020/4/1.
 *
 * @author XanderYe
 */
@Data
public class Activity {

    private Integer id;

    private String name;

    private String url;

    private Boolean expired;

    private List<Payload> payloadList;

    private Boolean auto;

    private Date expirationTime;
}
