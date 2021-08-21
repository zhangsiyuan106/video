package com.boot.Mapper;

import com.boot.pojo.Article;
import org.checkerframework.checker.units.qual.A;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ArticleRepository extends MongoRepository<Article,Long> {
    Article findArticlesById(long articleId);

    List<Article> findArticleByUserid(int userId);
    Page<Article> findAll(Pageable pageable);
    void deleteArticleById(long id);
    Article getArticleById(long articleId);

}
