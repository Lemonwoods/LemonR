package com.lemon.service;

import com.lemon.dao.pojo.Comment;
import com.lemon.vo.CommentVo;
import com.lemon.vo.param.PageParam;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment);

    List<CommentVo> getArticleCommentVo(Long articleId, PageParam pageParam);
}
