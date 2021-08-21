package com.boot.service.Impl;

import com.boot.Mapper.ArticleRepository;
import com.boot.Mapper.PictureRepository;
import com.boot.pojo.Article;
import com.boot.pojo.Picture;
import com.boot.service.ArticleService;
import com.boot.utils.FastDFSClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author memgzong
 * @create 2021/5/15
 */
@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private PictureRepository pictureRepository;
    @Autowired
    private FastDFSClient fastDFSClient;

    @Override
    public List<Article> getArticleList() {
       return articleRepository.findAll();
    }

    @Override
    public Page<Article> showArticle(Pageable pageable) {

        return articleRepository.findAll(pageable);
    }

    @Override
    public void eidtArticle(Article article) {
        articleRepository.save(article);
    }

    @Override
    public void delArticle(long id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article getArticleById(long id) {
      return articleRepository.getArticleById(id);
    }

    @Override
    public Iterable<Article> getArticleByUid(long id) {
        Iterable<Article> allById = articleRepository.findAllById(Collections.singleton(id));
        return allById;
    }

    /***
     * 关注或者取关作者
     * @param userId
     */
    @Override
    public void updateFollow(int userId) {
        Query query =   Query.query(Criteria.where("userid").is(userId));
        List<Article> articleByUserid = articleRepository.findArticleByUserid(userId);
        System.out.println(articleByUserid);
        Update update = new Update();
        for (int i = 0; i < articleByUserid.size(); i++) {
            if (articleByUserid.get(i).isFollowed()){
                update.set("isFollowed",false);
                break;
            }else {
                update.set("isFollowed",true);
                break;
            }
        }

        mongoTemplate.updateMulti(query,update,Article.class);

    }

    @Override
    public void deleteCollect(long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(articleId));
        Update update = new Update();
        update.set("isCollected",false);
        mongoTemplate.updateFirst(query,update, Article.class);
    }

    @Override
    public void addCollect(long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(articleId));
        Update update = new Update();
        update.set("isCollected",true);
        mongoTemplate.updateFirst(query,update, Article.class);
    }

    @Override
    public void deleteLike(long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(articleId));
        Update update = new Update();
        update.set("attitude",0);
        update.inc("likenum",-1);

        mongoTemplate.updateFirst(query,update, Article.class);
    }

    @Override
    public void addLike(long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(articleId));
        Update update = new Update();
        update.set("attitude",1);
        update.inc("likenum",1);
        mongoTemplate.updateFirst(query,update, Article.class);
    }


    /**
     * 文章评论数增加
     * @param articleId
     */
    @Override
    public void updateArticleReplyNum(long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(articleId));
        Update update = new Update();
        update.inc("replynum",1);
        mongoTemplate.updateFirst(query,update, Article.class);
    }

    @Override
    public void deleteArticleReplyNum(long articleId) {
        Query query =   Query.query(Criteria.where("_id").is(articleId));
        Update update = new Update();
        update.inc("replynum",-1);
        mongoTemplate.updateFirst(query,update, Article.class);
    }


    /**
     * 发布一篇文章
     * @param headLine  标题
     * @param content   内容
     * @param userId    用户Id
     * @param userName  用户名
     * @return
     */
    @Override
    public Article saveArticle(String headLine, String content,int userId, String userName,String imgUrl) {
        Article article = new Article();
        article.setHeadLine(headLine);
        article.setContent(content);
        article.setAttitude(0);
        article.setCollected(false);
        article.setFollowed(false);
        article.setLikenum(0);
        article.setPublishtime(new Date());
        article.setUserid(userId);
        article.setReplynum(0);
        article.setImgUrl(imgUrl);
        article.setUserName(userName);
        articleRepository.save(article);
        return article;
    }

    @Override
    public String savePicture(Picture picture) {
       Picture picture1 =  pictureRepository.save(picture);
       return picture1.getPictureUrl();
    }

    @Override
    public String saveArticleCover(Picture picture,long id) {
        Picture picture1 =  pictureRepository.save(picture);
        Query query =   Query.query(Criteria.where("imgUrl").is(""));
        Update update = new Update();
        update.set("imgUrl",picture1.getPictureUrl());
        mongoTemplate.updateFirst(query,update, Article.class);
       return picture1.getPictureUrl();

    }

    @Override
    public Page<Article> findCommentPage(int pageNo, int pageSize) {

        return articleRepository.findAll(PageRequest.of((pageNo-1),pageSize));
    }

    @Override
    public long findCountAtr() {
        return articleRepository.findAll().size();
    }

    @Override
    public void delArticleByIds(long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            articleRepository.deleteArticleById(ids[i]);
        }
    }



    @Override
    public List<Article> getArticleByUserId(int userId) {
        return articleRepository.findArticleByUserid(userId);
    }


    @Override
    public Article getArticle(long articleId) {
        return  articleRepository.findArticlesById(articleId);
    }



}
