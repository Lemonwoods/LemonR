package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemon.dao.mapper.LikeArticleMapper;
import com.lemon.dao.dos.LikeArticle;
import com.lemon.service.LikeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private LikeArticleMapper likeArticleMapper;

    @Override
    public Boolean isLike(Long userId, Long articleId) {
        LambdaQueryWrapper<LikeArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LikeArticle::getUserId, userId);
        lambdaQueryWrapper.eq(LikeArticle::getArticleId, articleId);
        LikeArticle likeArticle = likeArticleMapper.selectOne(lambdaQueryWrapper);
        return likeArticle != null;
    }

    @Override
    public List<Long> getLikedArticleIdByUserId(Long userId) {
        LambdaQueryWrapper<LikeArticle> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(LikeArticle::getUserId, userId);
        lambdaQueryWrapper.select(LikeArticle::getArticleId);
        List<LikeArticle> likeArticles = likeArticleMapper.selectList(lambdaQueryWrapper);
        List<Long> likedArticleId = new ArrayList<>();
        for(LikeArticle likeArticle:likeArticles) likedArticleId.add(likeArticle.getArticleId());
        return likedArticleId;
    }

    @Override
    public void addUserLikeArticle(Long userId, Long articleId) {
        LikeArticle likeArticle = new LikeArticle();
        likeArticle.setArticleId(articleId);
        likeArticle.setUserId(userId);
        likeArticle.setCreateDate(System.currentTimeMillis());
        likeArticleMapper.insert(likeArticle);

    }
}
