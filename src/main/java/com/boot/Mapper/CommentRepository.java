package com.boot.Mapper;

import com.boot.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author memgzong
 * @create 2021/5/15
 */
//与jpa 类似
public interface CommentRepository extends MongoRepository<Comment,String> {

    //根据上级idparentid 查询文章评论的分页列表 规范格式
    Page<Comment> findAll(Pageable pageable);
}
