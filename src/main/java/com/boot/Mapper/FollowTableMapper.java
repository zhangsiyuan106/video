package com.boot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.pojo.Follow;
import org.springframework.stereotype.Repository;

/*
关注关系
 */
@Repository
public interface FollowTableMapper  extends BaseMapper<Follow> {
}
