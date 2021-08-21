package com.boot.Mapper;

import com.boot.pojo.VideoComment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/19 18:17
 */
public interface VideoCommentRepository extends MongoRepository<VideoComment,Long> {
    List<VideoComment> findAllByVideoId(long videoId);
    VideoComment getVideoCommentById(long parentId);
    List<VideoComment> findAllByParentid(long parentId);
    void deleteAllByVideoId(long videoId);
}
