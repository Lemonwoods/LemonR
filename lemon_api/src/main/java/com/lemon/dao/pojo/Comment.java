package com.lemon.dao.pojo;

import lombok.Data;

@Data
public class Comment {
    private Long id;

    private String content;

    private Long createDate;

    private Long articleId;

    private Long fromUid;

    private Long parentId;

    private Long toUid;

    private Integer level;
}
