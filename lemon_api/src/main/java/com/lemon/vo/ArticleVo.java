package com.lemon.vo;

import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {
    private Long id;

    private Long create_date;

    private String summary;

    private String title;

    private String authorId;

    private Integer weight;

    private Integer viewCount;

    private Integer commentCount;

    private Integer likeCount;

    private String content;

    private CategoryVo categoryVo;

    private List<TagVo> tagVos;

}
