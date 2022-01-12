package com.lemon.controller;

import com.lemon.dao.pojo.Article;
import com.lemon.dao.pojo.User;
import com.lemon.service.ArticleService;
import com.lemon.service.TokenService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.ArticleVo;
import com.lemon.vo.ErrorCode;
import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("articles")
public class ArticleController {
    @Resource
    private ArticleService articleService;

    @Resource
    private TokenService tokenService;

    @PostMapping
    public Result getArticleList(@RequestHeader(value = "Authorization", defaultValue = "") String token,
                                 @RequestBody PageParamWithCondition pageParamWithCondition){
        Long userId;
        if(!token.equals("")){
            userId = null;
        }else{
            User user = tokenService.checkToken(token);
            userId = user.getId();
        }

        return Result.succeed(articleService.getArticleList(pageParamWithCondition, userId));
    }

    @PostMapping("users/{userId}/liked")
    public Result getArticleUserLiked(@PathVariable("userId")Long userId,
                                      @RequestBody PageParam pageParam){
        List<ArticleVo> articleVos = articleService.getArticleUserLiked(userId, pageParam);
        return Result.succeed(articleVos);
    }

    @PostMapping("users/{userId}/published")
    public Result getArticleUserPublished(@PathVariable("userId")Long userId,
                                          @RequestBody PageParam pageParam){
        List<ArticleVo> articleVos = articleService.getArticleUserPublished(userId, pageParam);
        return Result.succeed(articleVos);
    }

    @PostMapping("users/{userId}/commented")
    public Result getArticleUserCommented(@PathVariable("userId")Long userId,
                                          @RequestBody PageParam pageParam){
        List<ArticleVo> articleVos = articleService.getArticleUserCommented(userId, pageParam);
        return Result.succeed(articleVos);
    }

    @PostMapping("{articleId}/remove")
    public Result removeArticle(@PathVariable("articleId")Long articleId){
        Article article = articleService.findArticleById(articleId);
        if(article.getAuthorId().equals(UserThreadLocal.get().getId())) articleService.removeArticle(articleId);
        else return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        return Result.succeed(null);
    }
}
