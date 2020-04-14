package cn.xanderye.mapper;

import cn.xanderye.entity.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    void insert(Log log);
}
