package com.lemon.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.dao.pojo.Article;

public interface ArticleMapper extends BaseMapper<Article> {
    IPage<Article> getArticleList(Page<Article> page,
                                  Long tagId,
                                  Long categoryId,
                                  Long authorId,
                                  String year,
                                  String month);

    Integer getArticleTotalCount(Long tagId, Long categoryId, Long authorId, String year, String month);
}
