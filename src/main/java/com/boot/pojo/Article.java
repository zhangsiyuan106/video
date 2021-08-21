package com.boot.pojo;

import com.boot.utils.AutoInckey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="article")//可以省略，如果省略，则默认使用类名小写映射集合
public class Article implements Serializable {

    @Id
    @AutoInckey
    private long id;//主键 _id
    private String userName;//作者名称
    private String content;//文章内容
    private String headLine;//文章标题
    private int userid;//文章作者id
    private Date publishtime;//发布日期
    private Integer likenum;//点赞数
    private Integer replynum;//回复数
    private boolean isFollowed;//是否关注
    private boolean isCollected; //是否被收藏
    private int attitude; //
    private String imgUrl;//文章图片的地址
}
