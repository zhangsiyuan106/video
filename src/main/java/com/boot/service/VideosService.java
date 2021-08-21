package com.boot.service;



import com.boot.pojo.MediaFile;
import org.springframework.data.domain.Page;

import java.util.List;

public interface VideosService {

    void save(MediaFile mediaFile);

    List<MediaFile> findVideoById(long fileId);

    MediaFile findVideoByName(String filename);

    List<MediaFile> findAllVideo();
    Page<MediaFile> findVideoPage(Integer pageNo,Integer pageSize);
    void deleteMediaById(String id);
    void deleteAllMedia();
    List<MediaFile> findVideoByMediaId(String mediaId);

    List<MediaFile> findVideoByUserId(int userId);

    void addCollect(long fileId);

    void deleteCollect(long fileId);

    void updateFollow(int userId);


    void updateVideoReplyNum(long fileId);
    List<MediaFile> getMediaFileByMimeType(String mimeType);
    long findCountVideo();
}
