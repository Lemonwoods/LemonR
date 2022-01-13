package com.lemon.vo.param;

import lombok.Data;

import java.util.List;

@Data
public class PublishArticleParam {
    private String summary;
    private String title;
    private Long authorId;
    private Long categoryId;
    private List<Long> tagIdList;
    private String content;
    private String contentHtml;
}
