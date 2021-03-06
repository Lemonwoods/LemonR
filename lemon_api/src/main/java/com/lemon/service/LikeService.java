package com.lemon.service;

import java.util.List;

public interface LikeService {
    Boolean isLike(Long userId, Long articleId);

    List<Long> getLikedArticleIdByUserId(Long userId);

    void addUserLikeArticle(Long userId, Long articleId);

    Boolean isLikedArticle(Long articleId, String token);
}
