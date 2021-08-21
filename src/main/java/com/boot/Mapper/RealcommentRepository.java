package com.boot.Mapper;

import com.boot.pojo.Realcomment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author memgzong
 * @create 2021/5/24
 */
public interface RealcommentRepository  extends MongoRepository<Realcomment,Long> {
        List<Realcomment> findAllByArticleId(long articleId);
        List<Realcomment> findAllByParentid(long parentId);
        Realcomment getRealcommentById(long parentId);
        List<Realcomment> findAllByUserid(String userId);
        Page<Realcomment> findAll(Pageable pageable);
        void deleteAllByArticleId(long articleId);
        Realcomment findRealcommentById(long id);
}
