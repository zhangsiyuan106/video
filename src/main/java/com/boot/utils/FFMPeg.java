package com.boot.utils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类为对音频视频进行合成处理的工具类
 */
public class FFMPeg {

    private static String ffmpegEXE="D:\\Enviroment\\ffmpeg\\ffmpeg-N-102560-g4718d74c58-win64-gpl\\bin\\ffmpeg.exe";

    public FFMPeg(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }
    /**
     * 验证文件后缀名
     * @param type
     * @return
     */
    private boolean validFileType(String type){
        if ("mp4".equals(type)){
            return true;
        }
        return false;
    }

    /**
     * 验证是否为文件格式
     * @param path
     * @return
     */
    private boolean checkFile(String path){
        File file = new File(path);
        if (!file.isFile()){
            return false;
        }else {
            return true;
        }
    }
    // ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）

    /**
     * ffmpeg转换m3u8
     * @param folderUrl
     * @param fileName
     * @param fileFullName
     * @return
     */
    public static boolean processM3U8(String folderUrl,String fileName,String newUrl,String fileFullName){
        List commend = new ArrayList();
        commend.add("ffmpeg");
        commend.add("-i");
        commend.add(folderUrl+"\\"+fileName);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-hls_time");
        commend.add("60");
        commend.add("-hls_list_size");
        commend.add("0");
        commend.add("-c:a");
        commend.add("aac");
        commend.add("-strict");
        commend.add("-2");
        commend.add("-f");
        commend.add("hls");
        commend.add(newUrl+"\\"+fileFullName+".m3u8");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            int i = doWaifFor(p);
            System.out.println("------>" + i);
            p.destroy();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 任何视频转换MP4 （asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
     * @param folderUrl
     * @param fileName
     * @param newUrl
     * @param fileFullName
     * @return
     */
    public static boolean processMp4(String folderUrl,String fileName,String newUrl,String fileFullName){
        List commend = new ArrayList();
        commend.add("ffmpeg");
        commend.add("-i");
        commend.add(folderUrl+"\\"+fileName);
        commend.add("-c:v");
        commend.add("libx264");
        commend.add("-strict");
        commend.add("-2");
        commend.add(newUrl+"\\"+fileFullName+".mp4");
        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commend);
            Process p = builder.start();
            int i = doWaifFor(p);
            System.out.println("------>" + i);
            p.destroy();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 监听ffmpeg执行过程
     * @param p
     * @return
     */
    public static int doWaifFor(Process p){
        InputStream in = null;
        InputStream err = null;
        int exitValue = 1; //returned to caller when p is finished
        try {
            System.out.println("coming");
            in = p.getInputStream();
            err= p.getErrorStream();
            boolean finished = false;
            while (!finished){
                try {
                    while (in.available()>0){
                        Character c = new Character((char)in.read());
                        System.out.print(c);
                    }
                    while (err.available()>0){
                        Character c = new Character((char) err.read());
                        System.out.print(c);
                    }
                    exitValue = p.exitValue();
                    finished = true;
                }catch (Exception e){
                    Thread.currentThread().sleep(500);
                }
            }
        }catch (Exception e){
            System.out.println("doWaitFor();: unexpected exception - "
                    + e.getMessage());
        }finally {
            try {
                if (in!=null){
                    in.close();
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            if (err!=null){
                try {
                    err.close();
                }catch (Exception e){
                    System.out.println(e.getMessage());
                }
            }
        }
        return exitValue;
    }

}
