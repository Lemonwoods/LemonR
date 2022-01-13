package com.lemon.dao.pojo;

import lombok.Data;

@Data
public class Article {
    private Long id;

    private Long create_date;

    private String summary;

    private String title;

    private Long authorId;

    private Long categoryId;

    private Integer weight;

    private Integer viewCount;

    private Integer commentCount;

    private Integer likeCount;

    private String content;

    private String contentHtml;
}
