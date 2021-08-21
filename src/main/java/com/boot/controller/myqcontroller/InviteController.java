package com.boot.controller.myqcontroller;

import com.boot.constant.LexJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/invite")
public class InviteController {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/{uid}")
    public LexJSONResult indvited(@PathVariable("uid") int uid){
        String sql = "select u.userName from user u,invited t where u.id = t.inviteid and t.userid = "+uid;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);

        return LexJSONResult.ok(list);
    }

    @GetMapping("/{uid1}/invitedpage/{uid2}")
    public LexJSONResult invitedto(@PathVariable("uid1")int uid1,
                                   @PathVariable("uid2")int uid2, Model model){
        if(uid1==uid2){
            return LexJSONResult.msg("不可以给自己助力",3);
        }
        String sql = "select * from invited where userid = "+uid1 +" and inviteid = "+ uid2;
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        if(list.size() == 0){//没有助力过
            sql = "insert into invited(userid, inviteid) values (?,?)";
            Object[] objects = new Object[2];
            objects[0] = uid1;
            objects[1] = uid2;
            int update = jdbcTemplate.update(sql, objects);
            if(update>0) {
                return LexJSONResult.msg("助力成功",1);//助力成功
            }else {
                return LexJSONResult.msg("助力失败",0);//助力失败，重试
            }

        }else{
            return LexJSONResult.msg("已经助力过了",2);//已经助力过了
        }


    }


}
