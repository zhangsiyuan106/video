package com.boot.controller.myqcontroller;

import com.boot.service.UserLikeArticleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/like/article")
public class ArticleLikeController {
    @Resource
    private UserLikeArticleService redisService;


    /**
     * 点赞文章
     */
    @PostMapping("/{articleId}/{likedUserId}/{likedPoseId}")
    public Object likeArticle(@PathVariable("articleId") Long articleId,
                              @PathVariable("likedUserId") Long likedUserId,
                              @PathVariable("likedPoseId") Long likedPoseId) {
        redisService.likeArticle(articleId, likedUserId, likedPoseId);
        return articleId;
    }

    /**
     * 取消点赞
     */
    @DeleteMapping("/{articleId}/{likedUserId}/{likedPoseId}")
    public Object unlikeArticle(@PathVariable("articleId") Long articleId,
                                @PathVariable("likedUserId") Long likedUserId,
                                @PathVariable("likedPoseId") Long likedPoseId) {
        redisService.unlikeArticle(articleId, likedUserId, likedPoseId);
        return articleId;
    }

    /**
     * 统计用户总的文章点赞数
     */
    @GetMapping("/total/user/{likedUserId}")
    public Object countUserLike(@PathVariable("likedUserId") Long likedUserId) {
        return redisService.countUserLike(likedUserId);
    }

    /**
     * 统计某篇文章总点赞数
     */
    @GetMapping("/total/article/{articleId}")
    public Object countArticleLike(@PathVariable("articleId") Long articleId) {
        return redisService.countArticleLike(articleId);
    }
}
