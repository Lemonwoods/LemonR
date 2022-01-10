package com.lemon.service;

import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;

public interface ArticleService {
    Result getArticleList(PageParamWithCondition pageParamWithCondition);
}
