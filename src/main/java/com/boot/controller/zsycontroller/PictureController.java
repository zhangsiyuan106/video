package com.boot.controller.zsycontroller;

import com.boot.constant.LexJSONResult;
import com.boot.pojo.Picture;
import com.boot.service.Impl.PictureServiceImpl;
import com.boot.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * @Author: zsy
 * @Description
 * @Date: Create in 21:56 2021/6/15
 */
@RestController
@RequestMapping("/picture")
public class PictureController {
    @Autowired
    private PictureServiceImpl pictureService;
    @Autowired
    private FastDFSClient fastDFSClient;

    /**
     * 上传
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping("/upload")
    public String uploadFile(MultipartFile file) throws IOException {
        String s = fastDFSClient.uploadFile(file);
        System.out.println(s);
        Picture picture = new Picture(null,s,null);
        pictureService.save(picture);
        return s;
    }

}
