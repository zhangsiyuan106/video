package com.boot.service.Impl;

import com.boot.Mapper.PictureRepository;
import com.boot.pojo.Picture;
import com.boot.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 9:52 2021/6/16
 */
@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureRepository pictureRepository;
    @Override
    public List<Picture> getAllPicture() {
        return pictureRepository.findAll();
    }

    @Override
    public Picture getPictureById(String pictureId) {
       return pictureRepository.findAllByPictureId(pictureId);
    }

    @Override
    public void save(Picture picture) {
        pictureRepository.save(picture);
    }

    @Override
    public List<Picture> findAllByArticleId(long articleId) {
        return pictureRepository.findAllByArticleId(articleId);
    }

}
