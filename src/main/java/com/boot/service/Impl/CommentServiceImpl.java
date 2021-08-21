package com.boot.service.Impl;

import com.boot.Mapper.CommentRepository;
import com.boot.pojo.Comment;
import com.boot.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author memgzong
 * @create 2021/5/15
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存一个评论
     * @param comment
     */
    public void saveComment(Comment comment){

    //如果需要自定义主键，可以在这里指定主键；如果不指定主键，MongoDB会自动生成主键
    //设置一些默认初始值。。。
    //调用dao
        commentRepository.save(comment);
    }
    /**
     * 更新评论
     * @param comment
     */
    public void updateComment(Comment comment){
    //调用dao
        commentRepository.save(comment);
    }
    /**
     * 根据id删除评论
     * @param id
     */
    public void deleteCommentById(String id){
    //调用dao
       commentRepository.deleteById(id);
    }
    /**
     * 查询所有评论
     * @return
     */
    public List<Comment> findCommentList(){
    //调用dao
        return commentRepository.findAll();
    }
    /**
     * 根据id查询评论
     * @param id
     * @return
     */
    public Comment findCommentById(String id){
    //调用dao
        return commentRepository.findById(id).get();
    }

    public Page<Comment> findCommentPage(int pageNo,int pageSize){
        return commentRepository.findAll(PageRequest.of((pageNo-1),pageSize));
    }

    /**
     * 增加点赞数 使用mongoTemplate
     */
    public void updateLikeNum(String id){

        //查询条件 根据id查询到评论

        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likenum");//自增长 类似于 $inc
        mongoTemplate.updateFirst(query,update,Comment.class);
    }

    //根据文章查询评论
    @Override
    public List<Comment> findCommentByaid(String articleid) {
        Query query = new Query(Criteria.where("articleId").is(articleid));
        List<Comment> comments = mongoTemplate.find(query, Comment.class);

        return comments;
    }
}
