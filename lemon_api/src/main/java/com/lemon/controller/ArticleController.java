package com.lemon.controller;

import com.lemon.dao.pojo.Article;
import com.lemon.dao.pojo.User;
import com.lemon.service.ArticleService;
import com.lemon.service.LikeService;
import com.lemon.service.TokenService;
import com.lemon.utils.Log;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.ArticleVo;
import com.lemon.vo.ErrorCode;
import com.lemon.vo.Result;
import com.lemon.vo.param.ArticleQueryCondition;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;
import com.lemon.vo.param.PublishArticleParam;
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

    @Resource
    private LikeService likeService;

    @Log
    @PostMapping
    public Result getArticleList(@RequestBody PageParamWithCondition pageParamWithCondition){
        return Result.succeed(articleService.getArticleList(pageParamWithCondition));
    }

    @Log
    @PostMapping("totalCount")
    public Result getArticleTotalCount(@RequestBody ArticleQueryCondition articleQueryCondition){
        Integer res = articleService.getArticleTotalCount(articleQueryCondition);
        return Result.succeed(res);
    }

    @Log
    @GetMapping("{articleId}")
    public Result getArticleById(@PathVariable("articleId")Long articleId){
        ArticleVo articleVo = articleService.getArticleVoById(articleId);
        return Result.succeed(articleVo);
    }

    @Log
    @PostMapping("{articleId}/isLiked")
    public Result getIsLikedArticle(@PathVariable("articleId")Long articleId,
                                    @RequestHeader(value = "Authorization", defaultValue = "")String token){
        return Result.succeed(likeService.isLikedArticle(articleId, token));
    }

    @Log
    @PostMapping("users/{userId}/liked")
    public Result getArticleUserLiked(@PathVariable("userId")Long userId,
                                      @RequestBody PageParam pageParam){
        List<ArticleVo> articleVos = articleService.getArticleUserLiked(userId, pageParam);
        return Result.succeed(articleVos);
    }

    @Log
    @PostMapping("users/{userId}/published")
    public Result getArticleUserPublished(@PathVariable("userId")Long userId,
                                          @RequestBody PageParam pageParam){
        List<ArticleVo> articleVos = articleService.getArticleUserPublished(userId, pageParam);
        return Result.succeed(articleVos);
    }

    @Log
    @PostMapping("users/{userId}/commented")
    public Result getArticleUserCommented(@PathVariable("userId")Long userId,
                                          @RequestBody PageParam pageParam){
        List<ArticleVo> articleVos = articleService.getArticleUserCommented(userId, pageParam);
        return Result.succeed(articleVos);
    }

    @Log
    @PostMapping("{articleId}/remove")
    public Result removeArticle(@PathVariable("articleId")Long articleId){
        Article article = articleService.findArticleById(articleId);
        if(article.getAuthorId().equals(UserThreadLocal.get().getId())) articleService.removeArticle(articleId);
        else return Result.fail(ErrorCode.NO_PERMISSION.getCode(), ErrorCode.NO_PERMISSION.getMsg());
        return Result.succeed(null);
    }

    @Log
    @PostMapping("{articleId}/like/add")
    public Result addArticleLike(@PathVariable("articleId")Long articleId){
        articleService.addArticleLike(articleId);
        return Result.succeed(null);
    }

    @Log
    @PostMapping("{articleId}/like/remove")
    public Result removeArticleLike(@PathVariable("articleId")Long articleId){
        articleService.removeArticleLike(articleId);
        return Result.succeed(null);
    }

    @Log
    @PostMapping("{articleId}/viewCount/add")
    public Result addViewCount(@PathVariable("articleId")Long articleId){
        articleService.addViewCount(articleId);
        return Result.succeed(null);
    }

    @Log
    @PostMapping("publish")
    public Result publish(@RequestBody PublishArticleParam publishArticleParam){
        ArticleVo articleVo = articleService.publish(publishArticleParam);
        return Result.succeed(articleVo);
    }
}
