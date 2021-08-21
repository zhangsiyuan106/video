package com.boot.service;

import com.boot.pojo.Realcomment;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author memgzong
 * @create 2021/5/24
 */

public interface RealcommentService {

    /**
     * 保存一个评论
     * @param
     */
    Realcomment saveComment(long articleId,String userName,int userid,String articleContent);
    Realcomment saveSubComment(long parentId,String userName,int userid,String articleContent);

    public Realcomment findCommentById(long id);
    List<Realcomment> findCommentByArticleId(long articleId);
    List<Realcomment> findCommentByuserid(long userId);
    List<Realcomment> findAllByParentId(long parentId);
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
    public void deleteReplyNum(long id);

    Page<Realcomment> findCommentPage(int pageNo, int pageSize);

    void deleteCommentById(long id);
    long findCountComm();

    void deleteAllByArtId(long articleId);

    void delCommByIds(long[] ids);
    void updateAllArticleReplyNum(long[] ids);

}
