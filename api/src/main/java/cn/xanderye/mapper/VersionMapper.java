package cn.xanderye.mapper;

import cn.xanderye.entity.Version;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface VersionMapper {

    /**
     * 获取最新版本
     * @param
     * @return cn.xanderye.onestep.entity.Version
     * @author XanderYe
     * @date 2020-04-04
     */
    Version getLastVersion();

    /**
     * 更新版本
     * @param version
     * @return void
     * @author XanderYe
     * @date 2020-04-04
     */
    void insert(Version version);
}
