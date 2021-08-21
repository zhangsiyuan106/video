package com.boot.pojo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 文章评论实体类
 */

//把一个java类声明为mongodb的文档，可以通过collection参数指定这个类对应的文档。
//@Document(collection="mongodb 对应 collection 名")
// 若未加 @Document ，该 bean save 到 mongo 的 comment collection
// 若添加 @Document ，则 save 到 comment collection

@Document(collection="comment")//可以省略，如果省略，则默认使用类名小写映射集合
//复合索引  userid  nickname
@CompoundIndex( def = "{'userid': 1, 'nickname': -1}")
public class Comment implements Serializable {
    //主键标识，该属性的值会自动对应mongodb的主键字段"_id"，如果该属性名就叫“id”,则该注解可以省略，否则必须写
    @Id
    private String id;//主键 _id
    //该属性对应mongodb的字段的名字，如果一致，则无需该注解

    @Field("content") //字段名不一致
    private String content;//吐槽内容
    private Date publishtime;//发布日期
    //添加了一个单字段的索引
    @Indexed
    private String userid;//发布人ID

    private String nickname;//昵称
    private Date createdatetime;//评论的日期时间
    private Integer likenum;//点赞数
    private Integer replynum;//回复数
    private String state;//状态
    private String parentid;//上级ID
    private String articleid;
    private Boolean isLiking;


    public Comment(String id, String content, Date publishtime, String userid, String nickname, Date createdatetime, Integer likenum, Integer replynum, String state, String parentid, String articleid, Boolean isLiking) {
        this.id = id;
        this.content = content;
        this.publishtime = publishtime;
        this.userid = userid;
        this.nickname = nickname;
        this.createdatetime = createdatetime;
        this.likenum = likenum;
        this.replynum = replynum;
        this.state = state;
        this.parentid = parentid;
        this.articleid = articleid;
        this.isLiking = isLiking;
    }

    public Comment() {
    }

    //getter and setter.....

    public Boolean getLiking() {
        return isLiking;
    }

    public void setLiking(Boolean liking) {
        isLiking = liking;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
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
    public Date getCreatedatetime() {
        return createdatetime;
    }
    public void setCreatedatetime(Date createdatetime) {
        this.createdatetime = createdatetime;
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
    public String getParentid() {
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
    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    public String getArticleid() {
        return articleid;
    }
    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", publishtime=" + publishtime +
                ", userid='" + userid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", createdatetime=" + createdatetime +
                ", likenum=" + likenum +
                ", replynum=" + replynum +
                ", state='" + state + '\'' +
                ", parentid='" + parentid + '\'' +
                ", articleid='" + articleid + '\'' +
                ", isLiking=" + isLiking +
                '}';
    }
}
