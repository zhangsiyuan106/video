package com.boot.pojo;

import com.boot.utils.AutoInckey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

/**用户点赞、收藏、关注存储实体类
 * @author lm
 * @version 1.8
 * @date 2021/7/23 21:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user_operation")
public class UserOperation {
    @Id
    @AutoInckey
    private long id;//用户id
    private long[] likeArticle;//点赞的文章的id的数组
    private long[] followUser; //关注的人的id数组
    private long[] collectArticle;//收藏的文章的id的数组
    private long[] collectVideo; //收藏的视频的id的数组
    private long[] likeComment; //评论被点赞的id数组
    private long[] likeVideoComment;//视频被点赞的id数组
}
