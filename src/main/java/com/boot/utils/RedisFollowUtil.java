package com.boot.utils;

import org.springframework.stereotype.Component;

@Component
public class RedisFollowUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_FOLLOWER = "follower"; // 被关注（粉丝）
    private static final String PREFIX_FOLLOWEE = "followee"; // 关注的目标

    /**
     * 某个用户关注的up
     * followee:userId -> zset(entityId, now) 以当前关注的时间进行排序
     * @param userId
     * @return
     */
    public static String getFolloweeKey(int userId) {
        return PREFIX_FOLLOWEE + SPLIT + userId;
    }

    /**
     * 某个up拥有的粉丝
     * follower:entityId -> zset(userId, now)
     * @param entityId up的id
     * @return
     */
    public static String getFollowerKey( int entityId) {
        return PREFIX_FOLLOWER +  SPLIT + entityId;
    }


}
