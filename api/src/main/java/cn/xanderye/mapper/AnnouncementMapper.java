package cn.xanderye.mapper;

import cn.xanderye.entity.Announcement;
import cn.xanderye.entity.Log;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnnouncementMapper {

    void insert(Announcement announcement);

    Announcement getLatest();
}
