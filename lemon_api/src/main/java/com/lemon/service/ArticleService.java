package com.lemon.service;

import com.lemon.dao.pojo.Article;
import com.lemon.vo.ArticleVo;
import com.lemon.vo.Result;
import com.lemon.vo.param.ArticleQueryCondition;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;
import com.lemon.vo.param.PublishArticleParam;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> getArticleList(PageParamWithCondition pageParamWithCondition);

    List<ArticleVo> getArticleUserLiked(Long userId, PageParam pageParam);

    List<ArticleVo> getArticleUserPublished(Long userId, PageParam pageParam);

    List<ArticleVo> getArticleUserCommented(Long userId, PageParam pageParam);

    Article findArticleById(Long articleId);

    void removeArticle(Long articleId);

    void addArticleLike(Long articleId);

    void removeArticleLike(Long articleId);

    void addViewCount(Long articleId);

    void addCommentCount(Long articleId);

    void removeCommentCount(Long articleId);

    ArticleVo publish(PublishArticleParam publishArticleParam);

    Integer getArticleTotalCount(ArticleQueryCondition articleQueryCondition);

    ArticleVo getArticleVoById(Long articleId);
}
