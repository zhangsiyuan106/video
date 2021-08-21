package com.boot.pojo;

import com.boot.utils.AutoInckey;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Document("media_file")
@Accessors(chain = true)
public class MediaFile implements Serializable {
    /*
    文件id、名称、大小、文件类型、文件状态（未上传、上传完成、上传失败）、上传时间、视频处理方式、视频处理状态、hls_m3u8,hls_ts_list、课程视频信息（课程id、章节id）
     */
    @Id
    //文件id
     //字段名不一致
    @AutoInckey
    @Field("_id")
    private long fileId;
    //评论数量
    private int replyNum;
    //用作查询
    private String mediaId;
    //文件名称
    private String fileName;
    //文件原始名称
    private String fileOriginalName;
    //文件路径
    private String filePath;
    //文件url
    private String fileUrl;
    //文件类型
    private String fileType;
    //mimetype 视频类型
    private String mimeType;
    //文件大小
    private Long fileSize;
    //文件状态
    private String fileStatus;
    //播放次数
    private Integer playCount=0;
    //上传时间
    //Date
    private String uploadTime;
    //处理状态
    private String processStatus;
//    hls处理
    private MediaFileProcess_m3u8 mediaFileProcess_m3u8;

    //tag标签用于查询
    private String tag;
    //图片路径
    private String imgPath;
    //上传的用户id
    private Integer userId;
    private boolean playBol;
    //上传的用户名
    private String userName;

    private boolean isFollowed;//是否关注

    private boolean isCollected; //是否被收藏

    private int attitude; //
}
