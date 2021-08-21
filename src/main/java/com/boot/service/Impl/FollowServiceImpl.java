package com.boot.service.Impl;

import com.boot.pojo.Follow;
import com.boot.pojo.User;
import com.boot.service.FollowService;
import com.boot.service.FollowTableService;
import com.boot.utils.RedisFollowUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private RedisTemplate redisTemplate ;

    @Autowired
    private UserServiceImpl service;

    @Autowired
    private FollowTableService followTableService;

    //关注
    @Override
    public void follow(int userId, int aimId) {
        redisTemplate.execute( new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                // 生成 Redis 的 key
                String followeeKey = RedisFollowUtil.getFolloweeKey(userId);
                String followerKey = RedisFollowUtil.getFollowerKey(aimId);

                // 开启事务管理
                redisOperations.multi();
                /**
                 * followee:userId -> zset(entityId, now) 以当前关注的时间进行排序
                 * follower:entityId -> zset(userId, now)
                 */

                // 插入数据
                redisOperations.opsForZSet().add(followeeKey,String.valueOf(aimId),System.currentTimeMillis());

                redisOperations.opsForZSet().add(followerKey, String.valueOf(userId),System.currentTimeMillis());


                // 提交事务
                return redisOperations.exec();

            }
        });
    }
    //取消关注
    @Override
    public void unfollow(int userId, int aimId) {
        redisTemplate.execute(new SessionCallback() {

              @Override
              public Object execute(RedisOperations redisOperations) throws DataAccessException {

                  // 生成 Redis 的 key
                  String followeeKey = RedisFollowUtil.getFolloweeKey(userId);
                  String followerKey = RedisFollowUtil.getFollowerKey(aimId);

                  // 开启事务管理
                  redisOperations.multi();

                  // 删除数据
                  redisOperations.opsForZSet().remove(followeeKey,String.valueOf(aimId));
                  redisOperations.opsForZSet().remove(followerKey, String.valueOf(userId));
                  // 提交事务
                  return redisOperations.exec();
              }
          });
    }
    //查询某个用户关注的up的数量
    @Override
    public long findFolloweeCount(int userId) {
        String followeeKey = RedisFollowUtil.getFolloweeKey(userId);
        return redisTemplate.opsForZSet().zCard(followeeKey);
    }
    //查询某个实体的粉丝数量
    @Override
    public long findFollowerCount(int aimId) {
        String followerKey = RedisFollowUtil.getFollowerKey(aimId);
        return redisTemplate.opsForZSet().zCard(followerKey);
    }
    //判断当前用户是否已关注该up
    @Override
    public boolean hasFollowed(int userId, int aimId) {
        String followeeKey = RedisFollowUtil.getFolloweeKey(userId);
        return redisTemplate.opsForZSet().score(followeeKey, String.valueOf(aimId)) != null ;
    }
    //分页查询某个用户关注的人
    @Override
    public List<Map<String, Object>> findFollowees(int userId, int offset, int limit) {
        String followeeKey = RedisFollowUtil.getFolloweeKey(userId);
        Set<String> targetIds = redisTemplate.opsForZSet().reverseRange(followeeKey, offset, offset + limit - 1);
        if (targetIds == null) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (String targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();

            User user = service.getUserById(Integer.valueOf(targetId));
            map.put("user", user);
            Double score = redisTemplate.opsForZSet().score(followeeKey, targetId);
            map.put("followTime", new Date(score.longValue()));

            list.add(map);
        }

        return list;
    }
    //分页查询某个用户的粉丝
    @Override
    public List<Map<String, Object>> findFollowers(int userId, int offset, int limit) {
        String followerKey = RedisFollowUtil.getFollowerKey(userId);
        Set<String> targetIds = redisTemplate.opsForZSet().reverseRange(followerKey, offset, offset + limit - 1);
        if (targetIds == null) {
            return null;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (String targetId : targetIds) {
            Map<String, Object> map = new HashMap<>();

            User user = service.getUserById(Integer.valueOf(targetId));
            map.put("user", user);
            Double score = redisTemplate.opsForZSet().score(followerKey, targetId);
            map.put("followTime", new Date(score.longValue()));

            list.add(map);
        }

        return list;
    }

    @Override
    public void init() {
        List<Follow> finall = followTableService.finall();
        for (Follow follow : finall) {
            this.follow(follow.getUserid(),follow.getFollowid());
        }
    }

}
