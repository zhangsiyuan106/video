package com.boot.controller.lmcontroller;

import com.boot.constant.LexJSONResult;
import com.boot.pojo.Realcomment;
import com.boot.pojo.VideoComment;
import com.boot.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/19 18:29
 */
@RestController
@RequestMapping("/videoComment")
public class VideoCommentController {
    @Autowired
    private VideoCommentService videoCommentService;
    @RequestMapping("/addVideoComment")
    public LexJSONResult addVideoComment(@RequestParam("id") long videoId, @RequestParam("content") String videoContent, @RequestParam("username") String userName, @RequestParam("userid") int userid){
        VideoComment videoComment = videoCommentService.saveComment(videoId, userName, userid, videoContent);
        return LexJSONResult.ok(videoComment);
    }

    @RequestMapping("/addVideoSubComment")
    public LexJSONResult addVideoSubComment(@RequestParam("id") long parentId, @RequestParam("content") String videoContent, @RequestParam("username") String userName, @RequestParam("userid") int userid){
        VideoComment videoComment = videoCommentService.saveSubComment(parentId, userName, userid, videoContent);
        return LexJSONResult.ok(videoComment);
    }
    @RequestMapping("/getVideoCommByVideoId/{id}")
    public LexJSONResult getRealComment(@PathVariable("id") long id){
        List<VideoComment> commentByVideoId = videoCommentService.findCommentByVideoId(id);
        return LexJSONResult.ok(commentByVideoId);
    }

    @RequestMapping("/getVideoCommByParentId/{id}")
    public LexJSONResult getRealCommByParentId(@PathVariable("id") long parentId){
        List<VideoComment> commentByParentId = videoCommentService.findAllByParentId(parentId);
        return LexJSONResult.ok(commentByParentId);
    }

    @RequestMapping("/updateReplyNum/{commentId}")
    public LexJSONResult updateReplyNum(@PathVariable("commentId") long id){
        VideoComment commentById = videoCommentService.findCommentById(id);
        videoCommentService.updateReplyNum(id);
        return LexJSONResult.ok(commentById);
    }

    @GetMapping("/updateLikeNum")
    public LexJSONResult updateLikeNum(@RequestParam("id") long id){
        VideoComment commentById = videoCommentService.findCommentById(id);


        videoCommentService.updateLikeNum(id);

        return LexJSONResult.ok(commentById);
    }

    @GetMapping("/deleteLikeNum")
    public LexJSONResult deleteLikeNum(@RequestParam("id") long id){
        VideoComment commentById = videoCommentService.findCommentById(id);

        videoCommentService.deleteLikeNum(id);
        return LexJSONResult.ok(commentById);
    }

    @DeleteMapping("/deleteAll/{videoId}")
    public LexJSONResult deleteAll(@PathVariable("videoId") long videoId){
        videoCommentService.deleteAllByVideoId(videoId);
        return LexJSONResult.ok();

    }
}
