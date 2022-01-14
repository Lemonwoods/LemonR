package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.dao.dos.LikeArticle;
import com.lemon.dao.mapper.ArticleMapper;
import com.lemon.dao.pojo.Article;
import com.lemon.service.*;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.*;
import com.lemon.vo.param.PageParam;
import com.lemon.vo.param.PageParamWithCondition;
import com.lemon.vo.param.PublishArticleParam;
import javafx.scene.shape.Arc;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private TagService tagService;

    @Resource
    private LikeService likeService;

    @Resource
    private CommentService commentService;

    private ArticleVo transferToArticleVo(Article article, boolean needCategory, boolean needTag, boolean needContent){
        ArticleVo articleVo = new ArticleVo();
        BeanUtils.copyProperties(article, articleVo);

        if(!needContent) articleVo.setContent(null);

        if(needCategory){
            CategoryVo categoryVo = categoryService.findCategoryVoById(article.getCategoryId());
            articleVo.setCategoryVo(categoryVo);
        }

        if(needTag){
            List<TagVo> tagVos = tagService.findTagVoListByArticleId(article.getId());
            articleVo.setTagVos(tagVos);
        }

        return articleVo;
    }

    private ArticleVo transferToArticleVo(Article article, Long userId, boolean needCategory, boolean needTag, boolean needContent){
        ArticleVo articleVo = transferToArticleVo(article, needCategory, needTag, needContent);
        articleVo.setIsLike(likeService.isLike(userId, article.getId()));
        return articleVo;
    }

    private List<ArticleVo> transferToArticleVoList(List<Article> articles, boolean needCategory, boolean needTag, boolean needContent){
        List<ArticleVo> articleVos = new ArrayList<>(articles.size());
        for(Article article:articles) articleVos.add(transferToArticleVo(article, needCategory, needTag, needContent));
        return articleVos;
    }

    private List<ArticleVo> transferToArticleVoList(List<Article> articles, Long userId, boolean needCategory, boolean needTag, boolean needContent){
        List<ArticleVo> articleVos = new ArrayList<>(articles.size());
        for(Article article:articles) articleVos.add(transferToArticleVo(article, userId, needCategory, needTag, needContent));
        return articleVos;
    }

    private void changeArticleCountField(Long articleId,ArticleCountField field, Integer changeNum){
        Article article = articleMapper.selectById(articleId);
        UpdateWrapper<Article> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", articleId);
        updateWrapper.set(field.getFieldName(), getFieldCountValue(article, field)+changeNum);
        articleMapper.update(null, updateWrapper);
    }

    private int getFieldCountValue(Article article, ArticleCountField articleCountField){
        if(articleCountField.getFieldName().equals("like_count")) return article.getLikeCount();
        if(articleCountField.getFieldName().equals("view_count")) return article.getViewCount();
        if(articleCountField.getFieldName().equals("comment_count")) return article.getCommentCount();
        //todo 改进
        return 0;
    }

    private Article generateInitialArticle(){
        Article article = new Article();
        article.setCreate_date(System.currentTimeMillis());
        article.setLikeCount(0);
        article.setViewCount(0);
        article.setCommentCount(0);
        article.setWeight(0);
        return article;
    }

    @Override
    public List<ArticleVo> getArticleList(PageParamWithCondition pageParamWithCondition, Long userId) {
        Page<Article> page = new Page<>(pageParamWithCondition.getPageParam().getPage(), pageParamWithCondition.getPageParam().getPageSize());
        IPage<Article> articleIPage = articleMapper.getArticleList(
                page,
                pageParamWithCondition.getTagId(),
                pageParamWithCondition.getCategoryId(),
                pageParamWithCondition.getAuthorId(),
                pageParamWithCondition.getYear(),
                pageParamWithCondition.getMonth());

        List<Article> articles = articleIPage.getRecords();

        if(userId == null) return transferToArticleVoList(articles, true, true, true);
        return transferToArticleVoList(articles, userId, true, true, true);
    }

    @Override
    public List<ArticleVo> getArticleUserLiked(Long userId, PageParam pageParam) {
        Page<Article> articlePage = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        List<Long> likedArticleId = likeService.getLikedArticleIdByUserId(userId);
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Article::getId, likedArticleId);
        IPage<Article> articleIPage = articleMapper.selectPage(articlePage, lambdaQueryWrapper);
        List<Article> articles = articleIPage.getRecords();
        return transferToArticleVoList(articles, userId, true, true, true);
    }

    @Override
    public List<ArticleVo> getArticleUserPublished(Long userId, PageParam pageParam) {
        Page<Article> articlePage = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Article::getAuthorId, userId);
        IPage<Article> articleIPage = articleMapper.selectPage(articlePage, lambdaQueryWrapper);
        List<Article> articles = articleIPage.getRecords();
        return transferToArticleVoList(articles, userId, true, true, true);
    }

    @Override
    public List<ArticleVo> getArticleUserCommented(Long userId, PageParam pageParam) {
        Page<Article> articlePage = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        Set<Long> commentArticleId = commentService.getArticleIdSetByUserId(userId);
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Article::getId, commentArticleId);
        IPage<Article> articleIPage = articleMapper.selectPage(articlePage, lambdaQueryWrapper);
        List<Article> articles = articleIPage.getRecords();
        return transferToArticleVoList(articles, userId, true, true, true);
    }

    @Override
    public Article findArticleById(Long articleId) {
        return articleMapper.selectById(articleId);
    }

    @Override
    public void removeArticle(Long articleId) {
        articleMapper.deleteById(articleId);
    }

    @Override
    @Transactional
    public void addArticleLike(Long articleId) {
        changeArticleCountField(articleId, ArticleCountField.LIKE_COUNT, 1);
        likeService.addUserLikeArticle(UserThreadLocal.get().getId(), articleId);
    }

    @Override
    @Transactional
    public void removeArticleLike(Long articleId) {
        changeArticleCountField(articleId, ArticleCountField.LIKE_COUNT, -1);
        likeService.addUserLikeArticle(UserThreadLocal.get().getId(), articleId);
    }

    @Override
    public void addViewCount(Long articleId) {
        changeArticleCountField(articleId, ArticleCountField.VIEW_COUNT, 1);
    }

    @Override
    public void addCommentCount(Long articleId) {
        changeArticleCountField(articleId, ArticleCountField.COMMENT_COUNT, 1);
    }

    @Override
    public void removeCommentCount(Long articleId) {
        changeArticleCountField(articleId, ArticleCountField.COMMENT_COUNT, -1);
    }

    @Override
    @Transactional
    public ArticleVo publish(PublishArticleParam publishArticleParam) {
        Article articleResult = generateInitialArticle();
        BeanUtils.copyProperties(publishArticleParam, articleResult);
        articleMapper.insert(articleResult);
        tagService.addArticleTagRelation(publishArticleParam.getTagIdList(), articleResult.getId());
        return transferToArticleVo(articleResult, true, true, true);
    }
}
