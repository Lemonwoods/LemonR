package com.lemon.service;

import com.lemon.dao.pojo.Comment;
import com.lemon.vo.CommentVo;
import com.lemon.vo.param.PageParam;

import java.util.List;
import java.util.Set;

public interface CommentService {
    CommentVo addComment(Comment comment);

    List<CommentVo> getArticleCommentVo(Long articleId, PageParam pageParam);

    Set<Long> getArticleIdSetByUserId(Long userId);

    void removeComment(Long commentId);
}
