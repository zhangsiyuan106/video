package com.boot.service;

import com.boot.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*@author memgzong
*@create 2021/6/4
*/
@Service
public interface CommentService {

     void saveComment(Comment comment);
    /**
     * 更新评论
     * @param comment
     */
     void updateComment(Comment comment);
    /**
     * 根据id删除评论
     * @param id
     */
     public void deleteCommentById(String id);
    /**
     * 查询所有评论
     * @return
     */
    public List<Comment> findCommentList();
    /**
     * 根据id查询评论
     * @param id
     * @return
     */
    public Comment findCommentById(String id);

    public Page<Comment> findCommentPage(int pageNo, int pageSize);

    /**
     * 增加点赞数 使用mongoTemplate
     */
    public void updateLikeNum(String id);

    /**
     * 根据 文章名查询所有评论
     */
    List<Comment> findCommentByaid(String articleid);
}
