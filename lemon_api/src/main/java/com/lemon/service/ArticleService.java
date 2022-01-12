package com.lemon.service;

import com.lemon.dao.pojo.Article;
import com.lemon.vo.ArticleVo;
import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> getArticleList(PageParamWithCondition pageParamWithCondition, Long userId);

    List<ArticleVo> getArticleUserLiked(Long userId, PageParam pageParam);

    List<ArticleVo> getArticleUserPublished(Long userId, PageParam pageParam);

    List<ArticleVo> getArticleUserCommented(Long userId, PageParam pageParam);

    Article findArticleById(Long articleId);

    void removeArticle(Long articleId);
}
