package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemon.dao.mapper.LikeArticleMapper;
import com.lemon.dao.dos.LikeArticle;
import com.lemon.dao.pojo.User;
import com.lemon.service.LikeService;
import com.lemon.service.TokenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LikeServiceImpl implements LikeService {
    @Resource
    private LikeArticleMapper likeArticleMapper;

    @Resource
    private TokenService tokenService;

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

    @Override
    public Boolean isLikedArticle(Long articleId, String token) {
        if(token.equals("")||tokenService.checkToken(token)==null){
            return false;
        }

        User user = tokenService.checkToken(token);
        LambdaQueryWrapper<LikeArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LikeArticle::getArticleId, articleId);
        queryWrapper.eq(LikeArticle::getUserId, user.getId());
        LikeArticle likeArticle = likeArticleMapper.selectOne(queryWrapper);
        return likeArticle != null;
    }
}
