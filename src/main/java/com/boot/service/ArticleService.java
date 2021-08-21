package com.boot.service;

import com.boot.pojo.Article;
import com.boot.pojo.Picture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*@author memgzong
*@create 2021/6/4
*/
@Service
public interface ArticleService {
    List<Article> getArticleList();
    Article getArticle(long articleId);

    Page<Article> showArticle(Pageable pageable);

    void eidtArticle(Article article);

    void delArticle(long id);

    Article getArticleById(long id);
    List<Article> getArticleByUserId(int userId);
    Iterable<Article> getArticleByUid(long id);

    /**
     * 关注作者
     * @param userid
     */
    void updateFollow(int userid);


    /**
     * 取消收藏
     * @param articleId
     */
    void deleteCollect(long articleId);

    /**
     * 收藏
     * @param articleId
     */
    void addCollect(long articleId);

    /**
     * 取消点赞文章
     * @param articleId
     */
    void deleteLike(long articleId);

    /**
     * 点赞文章
     * @param articleId
     */
    void addLike(long articleId);

    void updateArticleReplyNum(long articleId);
    void deleteArticleReplyNum(long articleId);
    Article saveArticle(String headLine,String content,int userId,String userName,String imgUrl);

    /**
     * 保存文章的图片
     * @param picture
     * @return
     */
    String savePicture(Picture picture);

    /**
     * 增加文章封面
     * @param picture
     * @return
     */
    String saveArticleCover(Picture picture,long id);

    /**
     * 文章后台分页
     * @param pageNo
     * @param pageSize
     * @return
     */
     Page<Article> findCommentPage(int pageNo, int pageSize);

     long findCountAtr();
    void delArticleByIds(long[] ids);

}
