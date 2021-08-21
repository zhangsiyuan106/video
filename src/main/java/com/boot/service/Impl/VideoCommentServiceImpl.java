package com.boot.service.Impl;

import com.boot.Mapper.RealcommentRepository;
import com.boot.Mapper.VideoCommentRepository;
import com.boot.pojo.VideoComment;
import com.boot.service.VideoCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/19 18:21
 */
@Service
@Transactional
public class VideoCommentServiceImpl implements VideoCommentService {
    @Autowired
    private VideoCommentRepository videoCommentRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public VideoComment saveComment(long videoId,String userName,int userid,String videoContent) {
        VideoComment comment = new VideoComment();
        comment.setVideoId(videoId);
        comment.setContent(videoContent);
        comment.setLikenum(0);
        comment.setNickname(userName);
        comment.setReplynum(0);
        comment.setPublishtime(new Date());
        comment.setParentid(0);
        comment.setState(null);
        comment.setLiking(false);
        comment.setUserid(String.valueOf(userid));
        return  videoCommentRepository.save(comment);
    }

    /***
     * 保存子评论
     * @param parentId
     * @param userName
     * @param userid
     * @param videoContent
     * @return
     */
    public VideoComment saveSubComment(long parentId,String userName,int userid,String videoContent) {
        VideoComment comment = new VideoComment();
        VideoComment videoCommentByParentid = videoCommentRepository.getVideoCommentById(parentId);
        comment.setVideoId(videoCommentByParentid.getVideoId());
        comment.setContent(videoContent);
        comment.setLikenum(0);
        comment.setNickname(userName);
        comment.setReplynum(0);
        comment.setPublishtime(new Date());
        comment.setParentid(parentId);
        comment.setState(null);
        comment.setLiking(false);
        comment.setUserid(String.valueOf(userid));
        return  videoCommentRepository.save(comment);
    }
    @Override
    public VideoComment findCommentById(long id) {
        return videoCommentRepository.getVideoCommentById(id);
    }

    @Override
    public List<VideoComment> findCommentByVideoId(long videoId) {
        List<VideoComment> allByVideoId = videoCommentRepository.findAllByVideoId(videoId);
        List<VideoComment> baseVideoComment = new ArrayList<>();
        for (int i = 0; i < allByVideoId.size(); i++) {
            if (allByVideoId.get(i).getParentid() == 0){
                baseVideoComment.add(allByVideoId.get(i));
            }

        }
        return baseVideoComment;
    }



    @Override
    public List<VideoComment> findAllByParentId(long parentId) {
        return videoCommentRepository.findAllByParentid(parentId);
    }

    @Override
    public void updateLikeNum(long id) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likenum",1);//自增长 类似于 $inc
        update.set("isLiking",true);
        mongoTemplate.updateFirst(query,update, VideoComment.class);
    }

    @Override
    public void deleteLikeNum(long id) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("likenum",-1);//自减 类似于 $inc
        update.set("isLiking",false);
        mongoTemplate.updateFirst(query,update, VideoComment.class);
    }


    @Override
    public void updateReplyNum(long id) {
        Query query =   Query.query(Criteria.where("_id").is(id));
        Update update = new Update();
        update.inc("replynum",1);//自增 类似于 $inc
        mongoTemplate.updateFirst(query,update, VideoComment.class);
    }

    @Override
    public void deleteAllByVideoId(long videoId) {
        videoCommentRepository.deleteAllByVideoId(videoId);
    }
}
