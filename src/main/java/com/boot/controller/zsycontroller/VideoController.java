package com.boot.controller.zsycontroller;

import com.boot.constant.LexJSONResult;
import com.boot.pojo.Follow;
import com.boot.pojo.MediaFile;
import com.boot.pojo.User;
import com.boot.service.Impl.FollowTableServiceImpl;
import com.boot.service.Impl.UserServiceImpl;
import com.boot.service.VideosService;
import com.boot.utils.FFMPegCover;
import com.boot.utils.FileUploadTool;
import com.boot.utils.VideoUploadUtil;
import com.boot.websocket.SessionPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 16:10 2021/5/25
 */
@Controller
@RequestMapping("/video")
@RestController
public class VideoController {

    @Autowired
    private VideosService videosService;
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private FollowTableServiceImpl followService;

    //用户资源文件的目录
    private final  String  FILESPACE="E:/Video";

    //上传功能
    @PostMapping("/upload")
    public LexJSONResult uploadVideo(@RequestParam MultipartFile file,
                                     @RequestParam(value = "label") String label,
                                     @RequestParam(value= "userId") Integer userId){
        String originalFilename = file.getOriginalFilename();
        String suffix = StringUtils.substringAfter(originalFilename, ".");//获取后缀名
        String nameNotWithExt = FileUploadTool.getNameNotWithExt(originalFilename);//不带后缀名的文件名
        String uploadPath = FILESPACE + "/" + nameNotWithExt;

        User user = userService.getUserById(userId);
        LexJSONResult result = VideoUploadUtil.uploadVideo(file);
        try {
            //抽取图片
            FFMPegCover.convert(FILESPACE+"/"+nameNotWithExt+"/"+nameNotWithExt+".mp4",FILESPACE+"/"+nameNotWithExt+"/"+nameNotWithExt+".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (result!=null){
            MediaFile mediaFile = new MediaFile();
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(date);
            mediaFile.setFileOriginalName(originalFilename)
                    .setFileUrl(nameNotWithExt + "/" + nameNotWithExt+".mp4")
                    .setFileName(nameNotWithExt+".mp4")
                    .setFilePath(nameNotWithExt)
                    .setFileType(suffix)
                    .setUploadTime(format)
                    .setImgPath(nameNotWithExt+"/"+nameNotWithExt+".jpg")
                    .setTag("http://localhost:/video/")
                    .setMimeType(label)
                    .setUserId(userId)
                    .setUserName(user.getUserName());
            videosService.save(mediaFile);
        }
        //发布消息
        List<Follow> follows = followService.selectByFollowid(Integer.valueOf(userId));
        for (Follow follow : follows) {
            SessionPool.sendMessageByUserId(follow.getUserid()+"",user.getUserName()+"：发布了视频:"+originalFilename);
        }
        return  result;
    }
    //查询视频功能
    @GetMapping(value = "/list")
    public LexJSONResult findAll(){
        List<MediaFile> allVideo = videosService.findAllVideo();
//       System.out.println("请求过来了");
       return LexJSONResult.ok(allVideo);
    }
    //播放次数+1
    @GetMapping(value = "/list/playCount")
    public void addplayCount(@RequestParam("id") long id){
//        System.out.println(id);
        List<MediaFile> videoById = videosService.findVideoById(id);
        videoById.get(0).setPlayCount(videoById.get(0).getPlayCount()+1);
        videosService.save(videoById.get(0));
    }
    @GetMapping(value = "/findVideoByMediaId/{mediaId}")
    public LexJSONResult findVideoByMediaId(@PathVariable("mediaId") String mediaId){
        List<MediaFile> oneVideo = videosService.findVideoByMediaId(mediaId);
//        System.out.println("请求过来了");
        System.out.println(oneVideo);
        System.out.println(LexJSONResult.ok(oneVideo));
        return LexJSONResult.ok(oneVideo);
    }

    @GetMapping(value = "/findVideoByVideoId/{videoId}")
    public LexJSONResult findVideoByMediaId(@PathVariable("videoId") long VideoId){
        List<MediaFile> oneVideo = videosService.findVideoById(VideoId);
//        System.out.println("请求过来了");
//        System.out.println(oneVideo);
        System.out.println(LexJSONResult.ok(oneVideo));
        return LexJSONResult.ok(oneVideo);
    }

    //收藏了视频
    @GetMapping("/addCollect/{id}")
    public LexJSONResult addCollect(@PathVariable("id") long mediaId){
        videosService.addCollect(mediaId);
        System.out.println("收藏请求成功");
        return LexJSONResult.ok();
    }

    //取消收藏视频
    @GetMapping("/deleteCollect/{id}")
    public LexJSONResult deleteCollect(@PathVariable("id") long mediaId){
        videosService.deleteCollect(mediaId);
        System.out.println("取消·收藏请求成功");
        return LexJSONResult.ok();
    }


    //关注或取关作者
    @GetMapping("/addFollow/{id}")
    public LexJSONResult addFollow(@PathVariable("id") int  userid){
        videosService.updateFollow(userid);
        return LexJSONResult.ok();
    }
    @RequestMapping("/updateReplyNum/{id}")
    public LexJSONResult updateReplyNum (@PathVariable("id") long articleId){
        videosService.updateVideoReplyNum(articleId);
        return LexJSONResult.ok();
    }

}
