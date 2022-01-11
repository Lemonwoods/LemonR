package com.lemon.dao.dos;

import lombok.Data;

@Data
public class LikeArticle {
    private Long id;

    private Long userId;

    private Long articleId;

    private Long createDate;
}
