package com.boot.pojo;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/19 18:13
 */

/**
 * 视频评论实体类
 */

import com.boot.utils.AutoInckey;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 视频评论实体类
 */

//把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。
//@Document(collection="mongodb 对应 collection 名")
// 若未加 @Document ，该 bean save 到 mongo 的 comment collection
// 若添加 @Document ，则 save 到 comment collection

@Document(collection="video_Comment")//可以省略，如果省略，则默认使用类名小写映射集合
//复合索引  userid  nickname
@CompoundIndex( def = "{'userid': 1, 'nickname': -1}")
public class VideoComment implements Serializable {
    //主键标识，该属性的值会自动对应mongodb的主键字段"_id"，如果该属性名就叫“id”,则该注解可以省略，否则必须写
    @AutoInckey
    @Id
    private long id;//主键 _id
    //该属性对应mongodb的字段的名字，如果一致，则无需该注解

    @Field("content") //字段名不一致
    private String content;//吐槽内容
    private Date publishtime;//评论发布日期
    //添加了一个单字段的索引
    @Indexed
    private String userid;//发布人ID
    private String nickname;//昵称
    private Integer likenum;//点赞数
    private Integer replynum;//回复数
    private String state;//状态
    private long parentid;//上级ID
    private long videoId;
    private boolean isLiking;


    public VideoComment(long id, String content, Date publishtime, String userid, String nickname, Integer likenum, Integer replynum, String state, long parentid, long videoId, boolean isLiking) {
        this.id = id;
        this.content = content;
        this.publishtime = publishtime;
        this.userid = userid;
        this.nickname = nickname;

        this.likenum = likenum;
        this.replynum = replynum;
        this.state = state;
        this.parentid = parentid;
        this.videoId = videoId;
        this.isLiking = isLiking;
    }

    public VideoComment() {
    }

    //getter and setter.....

    public boolean getLiking() {
        return isLiking;
    }

    public void setLiking(boolean liking) {
        isLiking = liking;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getPublishtime() {
        return publishtime;
    }
    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public Integer getLikenum() {
        return likenum;
    }
    public void setLikenum(Integer likenum) {
        this.likenum = likenum;
    }
    public Integer getReplynum() {
        return replynum;
    }
    public void setReplynum(Integer replynum) {
        this.replynum = replynum;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public long getParentid() {
        /*说明：
        索引可以大大提升查询效率，一般在查询字段上添加索引，索引的添加可以通过Mongo的命令来添加，也可以在Java的实体类中通过注解添
        加。
        1）单字段索引注解@Indexed
                org.springframework.data.mongodb.core.index.Indexed.class
        声明该字段需要索引，建索引可以大大的提高查询效率。
        Mongo命令参考：
        2）复合索引注解@CompoundIndex
                org.springframework.data.mongodb.core.index.CompoundIndex.class
        复合索引的声明，建复合索引可以有效地提高多字段的查询效率。
        Mongo命令参考：
        5.6 文章评论的基本增删改查
        （1）创建数据访问接口 cn.itcast.article包下创建dao包，包下创建接口
        cn.itcast.article.dao.CommentRepository
        （2）创建业务逻辑类 cn.itcast.article包下创建service包，包下创建类
        cn.itcast.article.service.CommentService*/
        return parentid;
    }
    public void setParentid(long parentid) {
        this.parentid = parentid;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", publishtime=" + publishtime +
                ", userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", likenum=" + likenum +
                ", replynum=" + replynum +
                ", state='" + state + '\'' +
                ", parentid='" + parentid + '\'' +
                ", articleid='" + videoId + '\'' +
                ", isLiking=" + isLiking +
                '}';
    }
}
