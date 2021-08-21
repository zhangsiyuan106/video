package com.boot.service.Impl;

import com.boot.Mapper.RealcommentRepository;
import com.boot.pojo.Realcomment;
import com.boot.service.ArticleService;
import com.boot.service.RealcommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author memgzong
 * @create 2021/5/24
 */
@Service
@Transactional
public class RealcommentServiceImpl implements RealcommentService {
    @Autowired
    private RealcommentRepository realcommentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private ArticleService articleService;
    @Override
    public Realcomment saveComment(long articleId, String userName, int userId, String articleContent) {
        Realcomment comment = new Realcomment();
        comment.setArticleId(articleId);
        comment.setContent(articleContent);
        comment.setLikenum(0);
        comment.setNickname(userName);
        comment.setReplynum(0);
        comment.setPublishtime(new Date());
        comment.setParentid(0);
        comment.setState(null);
        comment.setLiking(false);
        comment.setUserid(String.valueOf(userId));
        return  realcommentRepository.save(comment);
    }

    @Override
    public Realcomment saveSubComment(long parentId, String userName, int userId, String articleContent) {
        Realcomment comment = new Realcomment();
        Realcomment videoCommentByParentId = realcommentRepository.getRealcommentById(parentId);
        comment.setArticleId(videoCommentByParentId.getArticleId());
        comment.setContent(articleContent);
        comment.setLikenum(0);
        comment.setNickname(userName);
        comment.setReplynum(0);
        comment.setPublishtime(new Date());
        comment.setParentid(parentId);
        comment.setState(null);
        comment.setLiking(false);
        comment.setUserid(String.valueOf(userId));
        return  realcommentRepository.save(comment);
    }

    @Override
    public Realcomment findCommentById(long id) {
        return   realcommentRepository.getRealcommentById(id);
    }

    @Override
    public List<Realcomment> findCommentByArticleId(long articleId) {
        List<Realcomment> allByArticleId = realcommentRepository.findAllByArticleId(articleId);
        List<Realcomment> baseVideoComment = new ArrayList<>();
        for (int i = 0; i < allByArticleId.size(); i++) {
            if (allByArticleId.get(i).getParentid() == 0){
                baseVideoComment.add(allByArticleId.get(i));
            }

        }
        return baseVideoComment;
    }

    @Override
    public List<Realcomment> findCommentByuserid(long userid) {
        List<Realcomment> allByuserId = realcommentRepository.findAllByUserid(Long.toString(userid));
        List<Realcomment> baseVideoComment = new ArrayList<>();
        for (int i = 0; i < allByuserId.size(); i++) {
            if (allByuserId.get(i).getParentid() == 0){
                baseVideoComment.add(allByuserId.get(i));
            }
        }
        return baseVideoComment;
    }

    @Override
    public List<Realcomment> findAllByParentId(long parentId) {
        return realcommentRepository.findAllByParentid(parentId);
    }

    @Override
    public void updateLikeNum(long id) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likenum");//自增长 类似于 $inc
        update.set("isLiking",true);
        mongoTemplate.updateFirst(query,update, Realcomment.class);
    }

    @Override
    public void deleteLikeNum(long id) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likenum",-1);//自减 类似于 $inc
        update.set("isLiking",false);
        mongoTemplate.updateFirst(query,update, Realcomment.class);
    }

    /**
     * 回复数自增
     * @param id
     */
    @Override
    public void updateReplyNum(long id) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("replynum",1);//自增 类似于 $inc
        mongoTemplate.updateFirst(query,update, Realcomment.class);
    }

    @Override
    public void deleteReplyNum(long id) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("replynum",-1);//自增 类似于 $inc
        mongoTemplate.updateFirst(query,update, Realcomment.class);
    }

    @Override
    public Page<Realcomment> findCommentPage(int pageNo, int pageSize) {
        return realcommentRepository.findAll(PageRequest.of((pageNo-1),pageSize));
    }

    @Override
    public void deleteCommentById(long id) {
        realcommentRepository.deleteById(id);
    }

    @Override
    public long findCountComm() {
        return realcommentRepository.findAll().size();
    }

    @Override
    public void deleteAllByArtId(long articleId) {
        realcommentRepository.deleteAllByArticleId(articleId);
    }

    @Override
    public void delCommByIds(long[] ids) {
        for (int i = 0; i < ids.length; i++) {
            Realcomment realcommentById = realcommentRepository.getRealcommentById(ids[i]);
            if (realcommentById.getParentid() == 0){
                realcommentRepository.deleteById(ids[i]);
                long articleId = realcommentById.getArticleId();
                articleService.deleteArticleReplyNum(articleId);

            }else {
                realcommentRepository.deleteById(ids[i]);
                deleteReplyNum(realcommentById.getParentid());
            }

        }
    }
    @Override
    public void updateAllArticleReplyNum(long[] ids) {
        for (int i = 0; i < ids.length; i++) {
//            System.out.println(ids[i]);
            Realcomment realcommentById = realcommentRepository.getRealcommentById(ids[i]);
//            System.out.println(realcommentById);
            long articleId = realcommentById.getArticleId();
//            System.out.println(articleId);
           articleService.deleteArticleReplyNum(articleId);
        }
    }

}
