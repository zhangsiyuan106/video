package com.boot.service;


import com.boot.pojo.VideoComment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/19 18:19
 */
@Service
@Transactional
public interface VideoCommentService {
    /**
     * 保存一个评论
     * @param
     */
    VideoComment saveComment(long videoId,String userName,int userid,String videoContent);
    VideoComment saveSubComment(long parentId,String userName,int userid,String videoContent);

    public VideoComment findCommentById(long id);
    List<VideoComment> findCommentByVideoId(long videoId);

    List<VideoComment> findAllByParentId(long parentId);
    /**
     * 增加点赞数 使用mongoTemplate
     */
    public void updateLikeNum(long id);


    public void deleteLikeNum(long id);

    /**
     * 回复了某条评论，使得父评论的回复数+1
     * @param id
     */
    public void updateReplyNum(long id);

    void deleteAllByVideoId(long videoId);

}
