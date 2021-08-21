package com.boot.controller.lmcontroller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.Article;
import com.boot.pojo.Follow;
import com.boot.pojo.Picture;
import com.boot.service.Impl.ArticleServiceImpl;
import com.boot.service.Impl.FollowTableServiceImpl;
import com.boot.service.Impl.PictureServiceImpl;
import com.boot.service.UserOperationService;
import com.boot.utils.FastDFSClient;
import com.boot.utils.OperaLog;
import com.boot.websocket.SessionPool;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
@Api(value = "文章管理")
public class ArticleController {
    @Autowired
    private ArticleServiceImpl articleService;
    @Autowired
    private PictureServiceImpl pictureService;
    @Autowired
    private FastDFSClient fastDFSClient;

    @Autowired
    private UserOperationService userOperationService;
    @Autowired
    private FollowTableServiceImpl followService;

    @RequestMapping("/list")
    public String getArticleList(){
        Map<String,Object> map= new HashMap<>();
        String flag = "error";
        List<Article> articleList = articleService.getArticleList();
        if (articleList != null){
            flag = "ok";
        }
        if (articleList == null){
            flag = "没找到信息";
        }
        map.put("flag",flag);
        map.put("articleList",articleList);
        String jsonString = JSON.toJSONString(map);
        return jsonString;
    }

    @GetMapping("/getArticle")
    public LexJSONResult getRealComment(@RequestParam("uid") long uid){
        Iterable<Article> articleByUid = articleService.getArticleByUserId((int)uid);
        return LexJSONResult.ok(articleByUid);
    }
    @GetMapping("/getArticleById")
    public LexJSONResult getArticleById(@RequestParam("id") long id){
        Article articleById = articleService.getArticleById(id);
        return LexJSONResult.ok(articleById);
    }
    //获取文字的图片信息
    @GetMapping("/getPicture")
    public LexJSONResult getPicture(@RequestParam("id") long id){
        List<Picture> allByArticleId = pictureService.findAllByArticleId(id);
        System.out.println(LexJSONResult.ok(allByArticleId));
        return LexJSONResult.ok(allByArticleId);
    }
    //分页显示
    @GetMapping("/article/show")
    public LexJSONResult showArticleList(Integer pageNum){
        if (pageNum == null){
            pageNum = 1;
        }
        Pageable pageable =PageRequest .of(pageNum-1,5);
        Page<Article> articles = articleService.showArticle(pageable);
//        System.out.println(articles);

        return LexJSONResult.ok(articles);
    }

    //查询
    @GetMapping("/article/{id}")
    public LexJSONResult detail(@PathVariable(name = "id") int id){
        Iterable<Article> articleById = articleService.getArticleByUid(id);

        return LexJSONResult.ok(articleById);
    }


    @PostMapping("/article/edit")
    public LexJSONResult  edit(@RequestBody Article article){
        Article temp = null;

        //没有id为编辑状态，否则添加
        if(article.getId() != 0){
            temp = articleService.getArticle(article.getId());
            //缺少权限编辑

        }else {
            //添加文章
            temp = article;
        }
       articleService.eidtArticle(temp);
        return LexJSONResult.ok();
    }

    @GetMapping("/article/del/{id}")
    public void del(@PathVariable(name = "id") long id){
        //权限删除：

        articleService.delArticle(id);

    }
    //关注作者
    @GetMapping("/addFollow/{id}")
    public LexJSONResult addFollow(@PathVariable("id") int userId){
        articleService.updateFollow(userId);
        return LexJSONResult.ok();
    }

    //收藏了文章
    @GetMapping("/addCollect/{id}")
    public LexJSONResult addCollect(@PathVariable("id") long articleId){
        articleService.addCollect(articleId);
        System.out.println("收藏请求成功");
        return LexJSONResult.ok();
    }

    //取消收藏文章
    @GetMapping("/deleteCollect/{id}")
    public LexJSONResult deleteCollect(@PathVariable("id") long articleId){
        articleService.deleteCollect(articleId);
        System.out.println("取消·收藏请求成功");
        return LexJSONResult.ok();
    }

    //点赞了文章
    @GetMapping("/addLike/{id}")
    public LexJSONResult addLike(@PathVariable("id") long articleId){
        articleService.addLike(articleId);
        return LexJSONResult.ok();
    }

    //取消点赞了文章
    @GetMapping("/deleteLike/{id}")
    public LexJSONResult deleteLike (@PathVariable("id") long articleId){
        articleService.deleteLike(articleId);
        return LexJSONResult.ok();
    }



    @RequestMapping("/updateReplyNum/{id}")
    public LexJSONResult updateReplyNum (@PathVariable("id") long articleId){
        articleService.updateArticleReplyNum(articleId);
        return LexJSONResult.ok();
    }
    @RequestMapping("/deleteArticleReplyNum/{id}")
    public LexJSONResult deleteArticleReplyNum (@PathVariable("id") long articleId){
        articleService.deleteArticleReplyNum(articleId);
        return LexJSONResult.ok();
    }

    @RequestMapping("/addArticle")
    public LexJSONResult releaseArticle(@RequestBody JSONObject jsonObject){
        String userName=jsonObject.get("userName").toString();
        String userId=jsonObject.get("userId").toString();
        String headLine=jsonObject.get("headLine").toString();
        String content=jsonObject.get("content").toString();
        String imgUrl=jsonObject.get("imgUrl").toString();

        int intUserId = Integer.valueOf(userId);
        Article article = articleService.saveArticle(headLine, content, intUserId, userName,imgUrl);
        //发布消息
        List<Follow> follows = followService.selectByFollowid(Integer.valueOf(userId));
        for (Follow follow : follows) {
            System.out.println(follow.getUserid());
            SessionPool.sendMessage(follow.getUserid()+"",userName+"：发布了一篇文章:"+headLine,article.getId());
        }

        return LexJSONResult.ok(article);
    }
    @RequestMapping("/picFile/upload")
    public LexJSONResult saveArticlePic(@RequestParam("image") MultipartFile file) throws IOException {
        String s = fastDFSClient.uploadFile(file);
        System.out.println(s);
        Picture picture = new Picture(null,s,null);
        String savePicture = articleService.savePicture(picture);

        return LexJSONResult.ok(savePicture);
    }


    @RequestMapping("/articleCover/upload")
    public LexJSONResult saveArticleCover(MultipartFile file) throws IOException {
        String data = fastDFSClient.uploadFile(file);
        System.out.println(data);
        Picture picture = new Picture(null,data,null);
//        String savePicture = articleService.saveArticleCover(picture,id);
        return LexJSONResult.ok(picture);
    }



    @GetMapping("/getArticleByPage/{pageNo}/{pageSize}")
    public String getCommentList(@PathVariable("pageNo")int pageNo, @PathVariable("pageSize")int pageSize){
        String flag = "error";
        Page<Article> articlePage = articleService.findCommentPage(pageNo, pageSize);
        long total = articlePage.getTotalElements();
        Map<String,Object> map= new HashMap<>();
        List<Article> articles = articlePage.getContent();
        if (articles != null){
            flag = "ok";
        }
        if (articles == null){
            flag = "没找到信息";
        }
        map.put("flag",flag);
        map.put("articleList",articles);
        map.put("totalArticle",total);

        String jsonString = JSON.toJSONString(map);
        return jsonString;
    }

    @OperaLog(module = "文章管理",description = "删除文章")
    @RequestMapping("/deleteArticleById/{id}")
    public String deleteArticleById(@PathVariable("id") long id){
        Article articleById = articleService.getArticleById(id);
        articleService.delArticle(id);
        System.out.println(articleById);
        int userid = articleById.getUserid();
        String headLine = articleById.getHeadLine();
        SessionPool.sendMessage(userid+"","文章已被管理员删除！",-1);


        return "success";
    }
    @DeleteMapping("/deleteArticleByIds/{id}")
    public LexJSONResult deleteArticleByIds(@PathVariable("id") long[] id, HttpServletRequest request){
        articleService.delArticleByIds(id);
        return LexJSONResult.ok();
    }
    @GetMapping("/getArtCount")
    public LexJSONResult getUserCount(){
        long userCount = articleService.findCountAtr();
        return LexJSONResult.ok(userCount);
    }
}
