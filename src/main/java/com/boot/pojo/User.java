package com.boot.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true) // Lombok链式编程
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;        //id

    @TableField(value = "userName")
    private String userName;     //用户名
    @TableField(value = "userPassword")
    private String userPassword; //密码

    private String nickname;     //网名
    private String avatar;       //头像
    @TableField(value = "backgroundImage")
    private String backgroundImage;     //背景图
    private String phone;        //电话
    private String email;        //邮箱
    private String sex;          //性别
    private String introduction;     //简介

    private String perms;       //权限


    @TableField(fill = FieldFill.INSERT, value = "createTime")
    private Date createTime;     //创建时间
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "updateTime")
    private Date updateTime;      //修改时间

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

}
