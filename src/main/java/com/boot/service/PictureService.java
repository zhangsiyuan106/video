package com.boot.service;

import com.boot.pojo.Picture;

import java.util.List;

public interface PictureService {
    List<Picture> getAllPicture();
    Picture getPictureById(String pictureId);
    void save(Picture picture);

    List<Picture> findAllByArticleId(long articleId);

}
