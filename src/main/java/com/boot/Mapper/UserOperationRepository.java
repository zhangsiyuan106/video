package com.boot.Mapper;

import com.boot.pojo.UserOperation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/23 21:55
 */
public interface UserOperationRepository extends MongoRepository<UserOperation,Long> {
    UserOperation findUserOperationById(long userId);
    List<UserOperation> findAllById(long id);

}
