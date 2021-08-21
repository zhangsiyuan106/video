package com.boot.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.boot.pojo.OperationLog;
import org.springframework.stereotype.Repository;

/**
 * @author lm
 * @version 1.8
 * @date 2021/8/17 20:00
 */
@Repository
public interface OperaLogRepository extends BaseMapper<OperationLog> {
}
