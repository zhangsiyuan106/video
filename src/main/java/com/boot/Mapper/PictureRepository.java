package com.boot.Mapper;

import com.boot.pojo.Picture;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 9:47 2021/6/16
 */
public interface PictureRepository extends MongoRepository<Picture, String> {
    Picture findAllByPictureId(String fileId);
    List<Picture> findAllByArticleId(long articleId);
}
