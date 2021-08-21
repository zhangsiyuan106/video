package com.boot.service.Impl;

import com.boot.Mapper.UserMapper;
import com.boot.Mapper.UserOperationRepository;
import com.boot.pojo.User;
import com.boot.pojo.UserOperation;
import com.boot.service.UserOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/23 21:57
 */
@Transactional
@Service
public class UserOperationServiceImpl implements UserOperationService {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private UserOperationRepository userOperationRepository;
    @Autowired
    private UserMapper userMapper;
    @Override
    public void addLikeArticle(long userId,long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(userId));
        Update update = new Update();
        update.push("likeArticle",articleId);

        mongoTemplate.upsert(query,update, UserOperation.class);

    }

    @Override
    public void deleteLikeArticle(long id, long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.pull("likeArticle",articleId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void addCollectionArticle(long id, long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.push("collectArticle",articleId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void deleteCollectionArticle(long id, long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.pull("collectArticle",articleId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public UserOperation findUserOperationById(long id) {
        UserOperation userOperationById = userOperationRepository.findUserOperationById(id);
        if (userOperationById != null){
            return userOperationById;
        }
        return null;
    }

    /**
     * 根据用户id，得到关注列表
     * @param userId
     * @return
     */
    @Override
    public  List<User> getUserFollowUserList(long userId){
        UserOperation userOperationById = userOperationRepository.findUserOperationById(userId);
        List<User> list = new ArrayList<>();
        if(userOperationById != null) {
            long[] userFollowId = userOperationById.getFollowUser();
//        System.out.println("总关注数" + userFollowId.length);
            User user = null;
            if (userFollowId != null) {
                for (long id : userFollowId) {
                    //关注的用户列表
                    user = userMapper.selectById(id);
                    if (user != null) {
                        list.add(user);
                    }
                }
            }
        }
        return list;
    }
    /**
     * 添加关注的用户的id，存入数据库
     * @param id
     * @param userId
     */
    @Override
    public void addFollowUser(long id, long userId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.push("followUser",userId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void deleteFollowUser(long id, long userId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.pull("followUser",userId);

        mongoTemplate.upsert(query,update, UserOperation.class);

    }

    @Override
    public void addCollectionVideo(long id, long videoId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.push("collectVideo",videoId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void deleteCollectionVideo(long id, long videoId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.pull("collectVideo",videoId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void addLikeComment(long id, long commentId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.push("likeComment",commentId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void delLikeComment(long id, long commentId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.pull("likeComment",commentId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void addLikeVideoComment(long id, long commentId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.push("likeVideoComment",commentId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }

    @Override
    public void delLikeVideoComment(long id, long commentId) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.pull("likeVideoComment",commentId);

        mongoTemplate.upsert(query,update, UserOperation.class);
    }
}
