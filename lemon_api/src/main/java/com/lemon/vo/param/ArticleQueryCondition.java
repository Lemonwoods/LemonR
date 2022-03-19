package com.lemon.vo.param;

import lombok.Data;

@Data
public class ArticleQueryCondition {
    private Long categoryId;
    private Long tagId;
    private Long authorId;

    private String year;
    private String month;
    private String day;
}
