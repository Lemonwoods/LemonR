package com.lemon.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
public enum ArticleCountField {
    LIKE_COUNT("like_count"),
    VIEW_COUNT("view_count"),
    COMMENT_COUNT("comment_count");

    private String fieldName;

    public String getFieldName(){
        return fieldName;
    }
}
