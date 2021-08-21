package com.boot.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于生成上传视频的封面图
 */
public class FFMPegCover {

    private static String ffmpegEXE="D:\\Enviroment\\ffmpeg\\ffmpeg-N-102560-g4718d74c58-win64-gpl\\bin\\ffmpeg.exe";

    public FFMPegCover(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    /**
     * 用于将视频抽出一帧作为封面
     *
     * @param in  视频地址
     * @param out 图片输出路径及图片名字
     * @throws IOException
     */
    public static void convert(String in, String out) throws IOException {
        File file = new File(in);
        if (!file.exists()) {
            System.err.println("路径[" + in + "]对应的视频文件不存在!");
            return;
        }

        List<String> commands = new java.util.ArrayList<String>();
        commands.add(ffmpegEXE);
        commands.add("-i");
        commands.add(in);
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
        commands.add("-ss");
        //添加参数＂-ss＂，该参数指定截取的起始时间
        commands.add("2");// 这个参数是设置截取视频多少秒时的画面
        commands.add("-t");// 添加参数＂-t＂，该参数指定持续时间
        commands.add("0.001");// 添加持续时间为1毫秒
        //commands.add("-s");
        //commands.add("700x525");
        commands.add(out);

        try {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
        } catch (Exception e) {
            e.printStackTrace();
//            log.error("获取视频封面图失败：" + e.getMessage());
            System.out.println("获取图片封面图失败" + e.getMessage());
            return;
        }

    }

}

