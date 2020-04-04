package cn.xanderye.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created on 2020/4/2.
 *
 * @author XanderYe
 */
@Data
public class Version {
    private String version;

    private String desc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;
}
