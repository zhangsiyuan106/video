package com.boot.service.Impl;

import com.boot.Mapper.FollowTableMapper;
import com.boot.pojo.Follow;
import com.boot.service.FollowTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class FollowTableServiceImpl implements FollowTableService {
    @Autowired
    private FollowTableMapper followTableMapper;

    @Override
    public void addfollow(int userid, int followid) {
        followTableMapper.insert(new Follow(userid,followid));
    }

    @Override
    public void del(int userid, int followid) {
        HashMap<String,Object> map  = new HashMap<>();
        map.put("userid",userid);
        map.put("followid",followid);
        followTableMapper.deleteByMap(map);
    }

    @Override
    public List<Follow>  selectByUserid(int userid) {
        HashMap<String,Object> map  = new HashMap<>();
        map.put("userid",userid);
        List<Follow> follows = followTableMapper.selectByMap(map);
        return follows;
    }

    public List<Follow>  selectByFollowid(int followid) {
        HashMap<String,Object> map  = new HashMap<>();
        map.put("followid",followid);
        List<Follow> follows = followTableMapper.selectByMap(map);
        return follows;
    }

    @Override
    public List<Follow> finall() {
        List<Follow> ff = followTableMapper.selectList(null);
        return ff;
    }
}
