package com.lemon.service;

import com.lemon.vo.ArticleVo;
import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;

import java.util.List;

public interface ArticleService {
    List<ArticleVo> getArticleList(PageParamWithCondition pageParamWithCondition);
}
