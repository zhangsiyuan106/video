package com.boot.controller.myqcontroller;

import com.boot.constant.LexJSONResult;
import com.boot.pojo.Article;
import com.boot.pojo.MediaFile;
import com.boot.pojo.Realcomment;
import com.boot.pojo.VideoComment;
import com.boot.service.Impl.ArticleServiceImpl;
import com.boot.service.Impl.VideoCommentServiceImpl;
import com.boot.service.Impl.VideosServiceImpl;
import com.boot.service.RealcommentService;
import com.boot.websocket.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/msg")
public class MsgController {
    @Autowired
    private RealcommentService realcommentService;

    @Autowired
    private ArticleServiceImpl articleService;

    @Autowired
    private VideoCommentServiceImpl videoCommentService;

    @Autowired
    private VideosServiceImpl videosService;



    @GetMapping("/article/{uid}")
    public LexJSONResult msg(@PathVariable("uid") int uid){
        List<Realcomment> result = null;
        //文章回复
        List<Article> articleByUserid = articleService.getArticleByUserId(uid);
        if(articleByUserid == null){
            return LexJSONResult.ok("没有更多了");
        }
        int i = 0;
        for (Article article : articleByUserid) {
            List<Realcomment> commentByArticleId = realcommentService.findCommentByArticleId(article.getId());
            if(commentByArticleId != null && i>0){
                result.addAll(commentByArticleId);
            }
            if(commentByArticleId != null && i==0){
                result = commentByArticleId;
                i++;
            }
        }
        return LexJSONResult.msg(result.size()+"",result);
    }

    @GetMapping("/reply/{uid}")
    public LexJSONResult rmsg(@PathVariable("uid") int uid){
        List<Realcomment> list = realcommentService.findCommentByuserid(uid);
//        System.out.println(list);
        List<Realcomment> reult = new ArrayList<>();
        for (Realcomment realcomment : list) {
            long id = realcomment.getId();
            List<Realcomment> allByParentId = realcommentService.findAllByParentId(id);
            reult.addAll(allByParentId);
        }
        return LexJSONResult.msg(reult.size()+"",reult);
    }

    @GetMapping("/video/{uid}")
    public LexJSONResult vmsg(@PathVariable("uid") int uid){
        List<MediaFile> videoByUserId = videosService.findVideoByUserId(uid);
        List<VideoComment> list = new ArrayList<>();
        for (MediaFile mediaFile : videoByUserId) {
            List<VideoComment> commentByVideoId = videoCommentService.findCommentByVideoId(mediaFile.getFileId());
            list.addAll(commentByVideoId);
        }
        return LexJSONResult.msg(list.size()+"",list);
    }

    @GetMapping("/{uid}")
    public LexJSONResult btn(@PathVariable("uid") int uid){
        SessionPool.sendMessage("haohao");
        return LexJSONResult.ok();
    }

}
