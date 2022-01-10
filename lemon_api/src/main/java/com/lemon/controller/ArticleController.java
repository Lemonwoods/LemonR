package com.lemon.controller;

import com.lemon.service.ArticleService;
import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @PostMapping
    public Result getArticleList(@RequestBody PageParamWithCondition pageParamWithCondition){
        return Result.succeed(articleService.getArticleList(pageParamWithCondition));
    }
}
