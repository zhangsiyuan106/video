package com.boot.service;

import com.boot.pojo.Follow;

import java.util.List;

/**
 * 关注表mysql
 *
 */
public interface FollowTableService {
    void addfollow(int userid,int followid);
    void del(int userid,int followid);
    //查询某人的关注列表
    List<Follow>  selectByUserid(int userid);
    //查询某人的粉丝列表
    List<Follow> selectByFollowid(int followid);
    //查询所有
    List<Follow> finall();
}
