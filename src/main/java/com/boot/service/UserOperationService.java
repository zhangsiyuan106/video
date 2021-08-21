package com.boot.service;

import com.boot.pojo.User;
import com.boot.pojo.UserOperation;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lm
 * @version 1.8
 * @date 2021/7/23 21:57
 */
@Service
public interface UserOperationService {
     void addLikeArticle(long id,long articleId);
     void deleteLikeArticle(long id,long articleId);
     List<User> getUserFollowUserList(long id);

     void addCollectionArticle(long id,long articleId);
     void deleteCollectionArticle(long id,long articleId);
     UserOperation findUserOperationById(long id);

     void addFollowUser(long id,long userId);
     void deleteFollowUser(long id,long userId);


     void addCollectionVideo(long id,long videoId);
     void deleteCollectionVideo(long id,long videoId);

     void addLikeComment(long id,long commentId);
     void delLikeComment(long id,long commentId);

     void addLikeVideoComment(long id,long commentId);
     void delLikeVideoComment(long id,long commentId);
}
