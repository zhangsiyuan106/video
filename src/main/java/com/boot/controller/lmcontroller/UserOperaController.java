package com.boot.controller.lmcontroller;

import com.boot.constant.LexJSONResult;
import com.boot.pojo.UserOperation;
import com.boot.service.UserOperationService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/23 22:29
 */
@RestController
@RequestMapping("/userOpera")
@Api(value = "用户点赞评论收藏操作")
public class UserOperaController {
    @Autowired
    private UserOperationService userOperationService;
    @RequestMapping("/addLikeArticle/{userId}/{articleId}")
    public LexJSONResult addLikeArticle(@PathVariable("userId") long userId,@PathVariable("articleId") long articleId){
        userOperationService.addLikeArticle(userId,articleId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/findLikeArticle/{userId}")
    public LexJSONResult findLikeArticle(@PathVariable("userId") long userId){
        UserOperation userOperationById = userOperationService.findUserOperationById(userId);
        return LexJSONResult.ok(userOperationById);
    }

    @RequestMapping("/deleteLikeArticle/{userId}/{articleId}")
    public LexJSONResult deleteLikeArticle(@PathVariable("userId") long userId,@PathVariable("articleId") long articleId){
        userOperationService.deleteLikeArticle(userId,articleId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/addCollectionArticle/{userId}/{articleId}")
    public LexJSONResult addCollectionArticle(@PathVariable("userId") long userId,@PathVariable("articleId") long articleId){
        userOperationService.addCollectionArticle(userId,articleId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/deleteCollectionArticle/{userId}/{articleId}")
    public LexJSONResult deleteCollectionArticle(@PathVariable("userId") long userId,@PathVariable("articleId") long articleId){
        userOperationService.deleteCollectionArticle(userId,articleId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/addFollowUser/{id}/{userId}")
    public LexJSONResult addFollowUser(@PathVariable("id") long id,@PathVariable("userId") long UserId){
        userOperationService.addFollowUser(id,UserId);
        return LexJSONResult.ok();
    }
    @RequestMapping("/delFollowUser/{id}/{userId}")
    public LexJSONResult delFollowUser(@PathVariable("id") long id,@PathVariable("userId") long UserId){
        userOperationService.deleteFollowUser(id,UserId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/addCollectionVideo/{userId}/{videoId}")
    public LexJSONResult addCollectionVideo(@PathVariable("userId") long userId,@PathVariable("videoId") long videoId){
        userOperationService.addCollectionVideo(userId,videoId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/deleteCollectionVideo/{userId}/{videoId}")
    public LexJSONResult deleteCollectionVideo(@PathVariable("userId") long userId,@PathVariable("videoId") long videoId){
        userOperationService.deleteCollectionVideo(userId,videoId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/addLikeComment/{userId}/{commentId}")
    public LexJSONResult addLikeComment(@PathVariable("userId") long userId,@PathVariable("commentId") long commentId){
        userOperationService.addLikeComment(userId,commentId);
        return LexJSONResult.ok();
    }
    @RequestMapping("/delLikeComment/{userId}/{commentId}")
    public LexJSONResult delLikeComment(@PathVariable("userId") long userId,@PathVariable("commentId") long commentId){
        userOperationService.delLikeComment(userId,commentId);
        return LexJSONResult.ok();
    }


    @RequestMapping("/addLikeVideoComment/{userId}/{commentId}")
    public LexJSONResult addLikeVideoComment(@PathVariable("userId") long userId,@PathVariable("commentId") long commentId){
        userOperationService.addLikeVideoComment(userId,commentId);
        return LexJSONResult.ok();
    }
    @RequestMapping("/delLikeVideoComment/{userId}/{commentId}")
    public LexJSONResult delLikeVideoComment(@PathVariable("userId") long userId,@PathVariable("commentId") long commentId){
        userOperationService.delLikeVideoComment(userId,commentId);
        return LexJSONResult.ok();
    }
}
