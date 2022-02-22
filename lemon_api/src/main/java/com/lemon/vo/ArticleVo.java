package com.lemon.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.sun.xml.internal.ws.developer.Serialization;
import lombok.Data;

import java.util.List;

@Data
public class ArticleVo {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long createDate;

    private String summary;

    private String title;

    private Long authorId;

    private String authorNickName;

    private Integer weight;

    private Integer viewCount;

    private Integer commentCount;

    private Integer likeCount;

    private String content;

    private CategoryVo categoryVo;

    private List<TagVo> tagVos;

}
