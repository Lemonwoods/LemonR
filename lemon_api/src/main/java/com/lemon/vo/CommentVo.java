package com.lemon.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long fromUid;

    private Long toUid;

    private String content;

    private Long createDate;

    private List<CommentVo> children;
}
