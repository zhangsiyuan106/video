package com.boot.Mapper;

import com.boot.pojo.MediaFile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MediaFileRepository extends MongoRepository<MediaFile,String> {

//    public MediaFile getMediaFileByFileId(String id);

    public MediaFile getMediaFileByFileName(String filename);

    public List<MediaFile> findAll();

    List<MediaFile> getMediaFileByMediaId(String mediaId);
    List<MediaFile> getMediaFileByFileId(long fileId);

    List<MediaFile> getMediaFileByUserId(int userId);
    List<MediaFile> getMediaFileByMimeType(String mimeType);
}
