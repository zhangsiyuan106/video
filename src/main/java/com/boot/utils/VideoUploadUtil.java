package com.boot.utils;

import com.boot.constant.LexJSONResult;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.*;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 16:13 2021/5/25
 */
public class VideoUploadUtil {

    //用户资源文件的目录
    private static final  String  FILESPACE="E:/Video";

    public static LexJSONResult uploadVideo(MultipartFile file){
        String url = null;
        try {
            String f = file.getOriginalFilename();//获取文件名
            String suffix = StringUtils.substringAfter(f, ".");//获取后缀名
            String nameNotWithExt = FileUploadTool.getNameNotWithExt(f);//不带后缀名的文件名
            String uploadPath = FILESPACE + "/" + nameNotWithExt;
            File fileFolder = new File(uploadPath);//上传后文件保存的路径
            if (!fileFolder.exists()){
                fileFolder.mkdirs();
            }
            approvalFile(file,fileFolder.toString());
            //这里要判断是否为MP4格式，进行格式转换
            if (!suffix.equals("mp4")){
                //转码
                boolean b = FFMPeg.processMp4(uploadPath, f,fileFolder.toString(),nameNotWithExt);
                if (!b){
                    return LexJSONResult.errorMsg("上传失败，系统转码异常");
                }
            }



        }catch (Exception e){
            e.printStackTrace();
            LexJSONResult.errorMsg("上传异常");
        }
        return LexJSONResult.ok();
    }

    /**
     * 创建空白的图片文件夹
     * @param path
     */
    public static void imgDirCreate(String path){
        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
    }
    /**
     * 将上传的文件保存到本地路径
     * @param filecontent
     * @param path
     */
    public static void approvalFile(MultipartFile filecontent, String path){
        OutputStream os = null;
        InputStream inputStream = null;
        String fileName = null;
        try {
            inputStream = filecontent.getInputStream();
            fileName = filecontent.getOriginalFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 2、保存到临时文件
            // 1K的数据缓冲
            byte[] bs = new byte[1024];
            // 读取到的数据长度
            int len;
            // 输出的文件流保存到本地文件
            File tempFile = new File(path);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
            os = new FileOutputStream(tempFile.getPath() + File.separator + fileName);
            // 开始读取
            while ((len = inputStream.read(bs)) != -1) {
                os.write(bs, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 完毕，关闭所有链接
            try {
                os.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
