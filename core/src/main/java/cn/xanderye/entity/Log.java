package cn.xanderye.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Created on 2020/4/14.
 *
 * @author 叶振东
 */
@Data
public class Log {
    private Integer id;

    private String ip;

    private String qq;

    private String area;

    private String character;

    private String version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date time;
}
