package com.boot.service;


import java.util.List;
import java.util.Map;

public interface FollowService {
    //关注
    void follow(int userId, int aimId);

    //取消关注
    void unfollow(int userId,  int aimId);

    //查询某个用户关注的up的数量
    long findFolloweeCount(int userId);

    //查询某个实体的粉丝数量
    long findFollowerCount(int aimId);

//    判断当前用户是否已关注该up
    boolean hasFollowed(int userId,  int aimId);

    //分页查询某个用户关注的人
    List<Map<String, Object>> findFollowees(int userId, int offset, int limit);

    //分页查询某个用户的粉丝
    List<Map<String, Object>> findFollowers(int userId, int offset, int limit);

    //初始化功能
    void init();

}
