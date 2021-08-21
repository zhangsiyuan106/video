package com.boot.controller.myqcontroller;

import com.boot.constant.LexJSONResult;
import com.boot.pojo.Article;
import com.boot.pojo.Follow;
import com.boot.pojo.Page;
import com.boot.pojo.User;
import com.boot.service.Impl.ArticleServiceImpl;
import com.boot.service.Impl.FollowServiceImpl;
import com.boot.service.Impl.FollowTableServiceImpl;
import com.boot.service.Impl.UserServiceImpl;
import com.boot.service.UserLikeArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FollowController {

    @Autowired
    FollowServiceImpl followService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FollowTableServiceImpl followTableService;

    @Autowired
    UserLikeArticleService userLikeArticleService;

    @Autowired
    private ArticleServiceImpl articleService;

    //关注
    @PostMapping("/follow/{userid}/{aimid}")
    public LexJSONResult follow(@PathVariable("userid")int userid, @PathVariable("aimid")int aimid){
        System.out.println("123456");
        followService.follow(userid,aimid);
        followTableService.addfollow(userid,aimid);
        return LexJSONResult.msg("关注成功",1);
    }

    //取消关注
    @PostMapping("/unfollow/{userid}/{aimid}")
    public LexJSONResult unfollow(@PathVariable("userid")int userid, @PathVariable("aimid")int aimid){
        followService.unfollow(userid,aimid);
        followTableService.del(userid,aimid);
        return LexJSONResult.msg("取消关注",1);
    }

    //某个用户的关注列表
    @GetMapping("/followees/{userId}")
    public LexJSONResult getFollowees(@PathVariable("userId") int userId, Page page, Model model){
        User user = userService.getUserById(userId);
        if(user==null){
            throw new RuntimeException("该用户不存在");
        }
        model.addAttribute("user",user);

        page.setLimit(5);
        page.setPath("/followees/" + userId);
        page.setRows((int) followService.findFolloweeCount(userId));

        // 获取关注列表
        List<Map<String, Object>> userList = followService.findFollowees(userId, page.getOffset(), page.getLimit());

        if (userList != null) {
            for (Map<String, Object> map : userList) {
                User u = (User) map.get("user"); // 被关注的用户
                map.put("hasFollowed",followService.hasFollowed(userId,u.getId())); // 判断当前登录用户是否已关注这个关注列表中的某个用户
            }
        }


        return LexJSONResult.msg(userList.size()+"",userList);
    }

    //某个用户的粉丝列表
    @GetMapping("/followers/{userId}")
    public LexJSONResult getFollowers(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }
        model.addAttribute("user", user);

        page.setLimit(5);
        page.setPath("/followers/" + userId);
        page.setRows((int) followService.findFollowerCount(userId));

        // 获取关注列表
        List<Map<String, Object>> userList = followService.findFollowers(userId, page.getOffset(), page.getLimit());

        if (userList != null) {
            for (Map<String, Object> map : userList) {
                User u = (User) map.get("user"); // 被关注的用户
                map.put("hasFollowed", followService.hasFollowed(userId,u.getId())); // 判断当前登录用户是否已关注这个关注列表中的某个用户
            }
        }


        return LexJSONResult.msg(userList.size()+"",userList);
    }

    @GetMapping("/init")
    public LexJSONResult init(){
        followService.init();
        return LexJSONResult.ok();
    }

    //个用户的粉丝列表
    @GetMapping("/followerstable/{userId}")
    public LexJSONResult followerstable(@PathVariable("userId") int userId){
        if(userId !=0){
            List<Follow> follows = followTableService.selectByUserid(userId);
            if(follows!=null){
            return LexJSONResult.msg(follows.size()+"",follows);
            }else {
                return LexJSONResult.msg(0+"",null);
            }
        }
        return LexJSONResult.msg("0",null);
    }
//关注列表
    @GetMapping("/followeestable/{userId}")
    public LexJSONResult followeestable(@PathVariable("userId") int userId){
        if(userId !=0){
        List<Follow> follows = followTableService.selectByFollowid(userId);
            if(follows!=null){
                return LexJSONResult.msg(follows.size()+"",follows);
            }else {
                return LexJSONResult.msg("0",null);
            }
        }
        return LexJSONResult.msg("0",null);
    }

    @GetMapping("/like/{userId}")
    public LexJSONResult likecount(@PathVariable("userId") int userId){
        List<Article> articleByUserId = articleService.getArticleByUserId(userId);
        int num = 0;
        if(articleByUserId != null) {
            for (Article article : articleByUserId) {
                num += article.getLikenum();
            }
        }

        return LexJSONResult.ok(num);
    }

}
