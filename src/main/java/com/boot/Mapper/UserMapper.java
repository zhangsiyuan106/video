package com.boot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.pojo.User;
import org.springframework.stereotype.Repository;


/**
 * @author hsw
 */
// 在对应的Mapper上面继承基本的类 BaseMapper
@Repository // 代表持久层
public interface UserMapper extends BaseMapper<User> {
}

