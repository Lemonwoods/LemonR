package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.dao.mapper.ArticleMapper;
import com.lemon.dao.pojo.Article;
import com.lemon.service.ArticleService;
import com.lemon.service.CategoryService;
import com.lemon.service.TagService;
import com.lemon.vo.ArticleVo;
import com.lemon.vo.CategoryVo;
import com.lemon.vo.Result;
import com.lemon.vo.TagVo;
import com.lemon.vo.param.PageParamWithCondition;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private TagService tagService;

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

    private List<ArticleVo> transferToArticleVoList(List<Article> articles, boolean needCategory, boolean needTag, boolean needContent){
        List<ArticleVo> articleVos = new ArrayList<>(articles.size());
        for(Article article:articles) articleVos.add(transferToArticleVo(article, needCategory, needTag, needContent));
        return articleVos;
    }

    @Override
    public List<ArticleVo> getArticleList(PageParamWithCondition pageParamWithCondition) {
        Page<Article> page = new Page<>(pageParamWithCondition.getPageParam().getPage(), pageParamWithCondition.getPageParam().getPageSize());
        IPage<Article> articleIPage = articleMapper.getArticleList(
                page,
                pageParamWithCondition.getTagId(),
                pageParamWithCondition.getCategoryId(),
                pageParamWithCondition.getAuthorId(),
                pageParamWithCondition.getYear(),
                pageParamWithCondition.getMonth());

        List<Article> articles = articleIPage.getRecords();
        return transferToArticleVoList(articles, true, true, true);
    }
}
