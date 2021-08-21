package com.boot.service.Impl;


import com.boot.Mapper.MediaFileRepository;
import com.boot.pojo.MediaFile;
import com.boot.service.VideosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zsy
 * @Description
 * @Date: Create in 20:42 2021/5/22
 */
@Service
public class VideosServiceImpl implements VideosService {

    @Autowired
    private MediaFileRepository mediaFileDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * 保存视频到数据库
     * @param mediaFile
     */
    public void save(MediaFile mediaFile) {
        mediaFileDao.save(mediaFile);

    }


    /**
     * 根据id查询视频
     * @param fileId 视频id
     * @return 视频结果
     */
    @Override
    public List<MediaFile> findVideoById(long fileId) {
        return mediaFileDao.getMediaFileByFileId(fileId);
    }

    /**
     * 根据视频名称查询
     * @param filename
     * @return
     */
    @Override
    public MediaFile findVideoByName(String filename) {
        return mediaFileDao.getMediaFileByFileName(filename);
    }

    /**
     * 查询所有视频
     * @return
     */
    @Override
    public List<MediaFile> findAllVideo() {
        return mediaFileDao.findAll();
    }

    @Override
    public Page<MediaFile> findVideoPage(Integer pageNo, Integer pageSize) {
        return mediaFileDao.findAll(PageRequest.of((pageNo-1),pageSize));
    }

    @Override
    public void deleteMediaById(String id) {
        mediaFileDao.deleteById(id);
    }

    @Override
    public void deleteAllMedia() {
        mediaFileDao.deleteAll();
    }

    @Override
    public List<MediaFile> findVideoByMediaId(String mediaId) {
        return  mediaFileDao.getMediaFileByMediaId(mediaId);
    }

    @Override
    public List<MediaFile> findVideoByUserId(int userId) {
        return mediaFileDao.getMediaFileByUserId(userId);
    }


    @Override
    public void deleteCollect(long videoId) {
        Query query =   Query.query(Criteria.where("_id").is(videoId));
        Update update = new Update();
        update.set("isCollected",false);

        mongoTemplate.updateFirst(query,update, MediaFile.class);
    }

    /***
     * 收藏视频
     * @param videoId
     */
    @Override
    public void addCollect(long videoId) {
        Query query =   Query.query(Criteria.where("_id").is(videoId));
        Update update = new Update();
        update.set("isCollected",true);
        mongoTemplate.updateFirst(query,update, MediaFile.class);
    }

    /***
     * 关注作者
     * @param userId
     */
    @Override
    public void updateFollow(int userId) {
        Query query =   Query.query(Criteria.where("userId").is(userId));
        List<MediaFile> mediaFileByUserId = mediaFileDao.getMediaFileByUserId(userId);
//        System.out.println(mediaFileByUserId);
        Update update = new Update();
        for (int i = 0; i < mediaFileByUserId.size(); i++) {
                if (mediaFileByUserId.get(i).isFollowed()){
                    update.set("isFollowed",false);
                    break;
                }else {
                    update.set("isFollowed",true);
                    break;
                }
        }

        mongoTemplate.updateMulti(query,update,MediaFile.class);

    }

    /**
     * 视频的评论数加一
     * @param fileId
     */
    @Override
    public void updateVideoReplyNum(long fileId) {
        Query query =   Query.query(Criteria.where("_id").is(fileId));
        Update update = new Update();
        update.inc("replyNum",1);
        mongoTemplate.updateFirst(query,update, MediaFile.class);
    }

    @Override
    public List<MediaFile> getMediaFileByMimeType(String mimeType) {
        return mediaFileDao.getMediaFileByMimeType(mimeType);
    }

    @Override
    public long findCountVideo() {
        return mediaFileDao.findAll().size();
    }

    /**
     * 模糊查询
     */
    public List<MediaFile> getLikeMediaFile(String name)
    {
            Query query = new Query();
            Criteria cname= new Criteria();
            cname.and("fileName").regex("^.*" +name+ ".*$");
            query.addCriteria(cname);
            List<MediaFile> mediaFiles = mongoTemplate.find(query,MediaFile.class, "fileName");
            return mediaFiles;
    }
}
