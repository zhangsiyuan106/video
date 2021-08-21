package com.boot.controller.lmcontroller;

import com.alibaba.fastjson.JSON;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.Comment;
import com.boot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author memgzong
 * @create 2021/5/24
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @GetMapping("/getCommentList/{pageNo}/{pageSize}")
    public String getCommentList(@PathVariable("pageNo")int pageNo, @PathVariable("pageSize")int pageSize){
        String flag = "error";
        Page<Comment> commentPage = commentService.findCommentPage(pageNo, pageSize);
        long total = commentPage.getTotalElements();
        Map<String,Object> map= new HashMap<>();
        List<Comment> content = commentPage.getContent();
        map.put("commList",content);
        if (content != null){
            flag = "ok";
        }
        if (content == null){
            flag = "没找到信息";
        }
        map.put("flag",flag);
        map.put("commList",content);
        map.put("totalComments",total);

        String jsonString = JSON.toJSONString(map);
        return jsonString;
    }

    @DeleteMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") String id){
        commentService.deleteCommentById(id);
        return "success";
    }
    @GetMapping("/addComment")
    public LexJSONResult addComment(@RequestParam("id") String articleId,@RequestParam("content") String articleContent){
        Comment comment = new Comment();
        comment.setArticleid(articleId);
        comment.setContent(articleContent);
        comment.setCreatedatetime(new Date());
        comment.setLikenum(0);
        comment.setNickname("xxx");
        comment.setReplynum(0);
        comment.setPublishtime(null);
        comment.setParentid(null);
        comment.setState(null);
        comment.setLiking(false);
        comment.setUserid("3");
        commentService.saveComment(comment);
        return LexJSONResult.ok();
    }

    @GetMapping("/findComment/{articleid}")
    public LexJSONResult findCommentByaid(@PathVariable("articleid") String articleid){
        List<Comment> commentByaid = commentService.findCommentByaid(articleid);
        return LexJSONResult.ok(commentByaid);
    }
}
