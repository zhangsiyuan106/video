package com.boot.controller.lmcontroller;

import com.alibaba.fastjson.JSON;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.Realcomment;
import com.boot.service.Impl.FollowTableServiceImpl;
import com.boot.service.Impl.UserServiceImpl;
import com.boot.service.RealcommentService;
import com.boot.utils.OperaLog;
import com.boot.websocket.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author memgzong
 * @create 2021/5/24
 */
@RestController
@RequestMapping("/relComment")
public class RealCommentController {
    @Autowired
    private FollowTableServiceImpl followService;
    @Autowired
    private RealcommentService realcommentService;
    @Autowired
    private UserServiceImpl userService;
    @GetMapping("/getCommentList/{pageNo}/{pageSize}")
    public String getCommentList(@PathVariable("pageNo")int pageNo, @PathVariable("pageSize")int pageSize){
        String flag = "error";
        Page<Realcomment> commentPage = realcommentService.findCommentPage(pageNo, pageSize);
        long total = commentPage.getTotalElements();
        Map<String,Object> map= new HashMap<>();
        List<Realcomment> content = commentPage.getContent();
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
    @GetMapping("/getComment/{id}")
    public  String getComment(@PathVariable("id")long id){
        Realcomment commentById = realcommentService.findCommentById(id);


        if (commentById != null) {
            String toJSONString = JSON.toJSONString(commentById);

            return toJSONString;
        }
        return "error";


    }

    @GetMapping("/getRealComm/{id}")
    public LexJSONResult getRealComment(@PathVariable("id") long id){
        List<Realcomment> commentByArticleId = realcommentService.findCommentByArticleId(id);
//        System.out.println(LexJSONResult.ok(commentByArticleId));
        return LexJSONResult.ok(commentByArticleId);
    }
    @GetMapping("/getRealCommByParentId/{id}")
    public LexJSONResult getRealCommByParetId(@PathVariable("id") long parentId){
        List<Realcomment> commentByArticleId = realcommentService.findAllByParentId(parentId);
        System.out.println(LexJSONResult.ok(commentByArticleId));
        return LexJSONResult.ok(commentByArticleId);
    }
    @GetMapping("/updateLikeNum")
    public LexJSONResult updateLikeNum(@RequestParam("id") long id){
        Realcomment commentById = realcommentService.findCommentById(id);


        realcommentService.updateLikeNum(id);

        return LexJSONResult.ok(commentById);
    }

    @GetMapping("/deleteLikeNum")
    public LexJSONResult deleteLikeNum(@RequestParam("id") long id){
        Realcomment commentId = realcommentService.findCommentById(id);

        realcommentService.deleteLikeNum(id);
        return LexJSONResult.ok(commentId);
    }
    @GetMapping("/addRealComment")
    public LexJSONResult addRealComment(@RequestParam("id") long articleId,@RequestParam("content") String articleContent,
                                        @RequestParam("username") String userName,@RequestParam("userid") int userId){

        Realcomment realcomment = realcommentService.saveComment(articleId, userName, userId, articleContent);
        return LexJSONResult.ok(realcomment);
    }

    @GetMapping("/addRealCommentReply")
    public LexJSONResult addRealCommentReply(@RequestParam("id") long parentId, @RequestParam("content") String articleContent,
                                             @RequestParam("username") String userName, @RequestParam("userid") int userId){
        Realcomment realcomment = realcommentService.saveSubComment(parentId, userName, userId, articleContent);
        Realcomment commentById = realcommentService.findCommentById(parentId);
        SessionPool.sendMessage(commentById.getUserid()+"",userName+"：回复了你的消息"+articleContent,commentById.getArticleId());
        return LexJSONResult.ok(realcomment);
    }


    @GetMapping("/updateReplyNum/{commentId}")
    public LexJSONResult updateReplyNum(@PathVariable("commentId") long id){
        Realcomment commentById = realcommentService.findCommentById(id);
        realcommentService.updateReplyNum(id);
        return LexJSONResult.ok(commentById);
    }
    @GetMapping("/deleteReplyNum/{commentId}")
    public LexJSONResult deleteReplyNum(@PathVariable("commentId") long id){
        Realcomment commentById = realcommentService.findCommentById(id);
        realcommentService.deleteReplyNum(id);
        return LexJSONResult.ok(commentById);
    }

    @OperaLog(module = "评论管理",description = "删除评论")
    @DeleteMapping("/deleteComment/{id}")
    public String deleteComment(@PathVariable("id") long id){
        Realcomment commentById = realcommentService.findCommentById(id);
        String content = commentById.getContent();
        String userid = commentById.getUserid();
        long articleId = commentById.getArticleId();
        realcommentService.deleteCommentById(id);

        SessionPool.sendMessage(userid+"","包含违规信息，审核未通过",articleId);

        return "success";
    }

    @GetMapping("/getCommCount")
    public LexJSONResult getUserCount(){
        long  commCount= realcommentService.findCountComm();
        return LexJSONResult.ok(commCount);
    }

    @DeleteMapping("/deleteAll/{artId}")
    public LexJSONResult deleteAll(@PathVariable("artId") long articleId){
        realcommentService.deleteAllByArtId(articleId);
        return LexJSONResult.ok();

    }

    @DeleteMapping("/deleteCommByIds/{id}")
    public LexJSONResult deleteCommByIds(@PathVariable("id") long[] ids){
        realcommentService.delCommByIds(ids);
        return LexJSONResult.ok();

    }

    @RequestMapping( "/updateAllReplyNum/{ids}")
    public LexJSONResult updateAllReplyNum(@PathVariable("ids") long[] ids){
        System.out.println(Arrays.toString(ids));
        realcommentService.updateAllArticleReplyNum(ids);
        return LexJSONResult.ok();

    }
}
