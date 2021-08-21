package com.boot.controller.lmcontroller;

import com.alibaba.fastjson.JSON;
import com.boot.constant.LexJSONResult;
import com.boot.pojo.MediaFile;
import com.boot.service.VideosService;
import com.boot.utils.OperaLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author memgzong
 * @create 2021/5/30
 */
@RestController
@RequestMapping("/videoMaster")
public class MediaFileController {
    @Autowired
    private VideosService videosService;


    @GetMapping("/getVideoList/{pageNo}/{pageSize}")
    public String getVideoList(@PathVariable("pageNo")int pageNo, @PathVariable("pageSize")int pageSize){
        String flag = "error";
        Page<MediaFile> commentPage = videosService.findVideoPage(pageNo, pageSize);
        long total = commentPage.getTotalElements();
        Map<String,Object> map= new HashMap<>();
        List<MediaFile> content = commentPage.getContent();
        List<Date> uploadTimes = new ArrayList<>();

        if (content != null){
            flag = "ok";
        }
        if (content == null){
            flag = "没找到信息";
        }
        map.put("flag",flag);
        map.put("mediaList",content);
        map.put("totalMedias",total);
        map.put("uploadTimes",uploadTimes);
        String jsonString = JSON.toJSONString(map);
        return jsonString;
    }


    @OperaLog(module = "视频管理",description = "下架视频")
    @DeleteMapping("/deleteMedia/{id}")
    public String deleteMedia(@PathVariable("id") String id){
        videosService.deleteMediaById(id);
        return "success";
    }


    @DeleteMapping("/deleteAllMedia")
    public String deleteAllMedia(HttpServletRequest request){
        videosService.deleteAllMedia();

        return "success";
    }

    @GetMapping("/getMediaByMimeType")
    public LexJSONResult getMediaByMimeType(@RequestParam("mimeType") String mimeType){
        List<MediaFile> mediaFileByMimeType = videosService.getMediaFileByMimeType(mimeType);
        return LexJSONResult.ok(mediaFileByMimeType);
    }

    @GetMapping("/getMediaCount")
    public LexJSONResult getUserCount(){
        long  mediaCount= videosService.findCountVideo();
        return LexJSONResult.ok(mediaCount);
    }
}
