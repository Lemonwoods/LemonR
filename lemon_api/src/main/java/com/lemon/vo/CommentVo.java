package com.lemon.vo;

import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    private Long id;

    private Long fromUid;

    private Long toUid;

    private String content;

    private Long createDate;

    private List<CommentVo> children;
}
