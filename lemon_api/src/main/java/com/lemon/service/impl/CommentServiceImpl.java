package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.dao.mapper.CommentMapper;
import com.lemon.dao.pojo.Comment;
import com.lemon.service.CommentService;
import com.lemon.vo.CommentVo;
import com.lemon.vo.param.PageParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    private CommentMapper commentMapper;

    private List<Comment> getChildrenComment(Comment comment){
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getParentId, comment.getId());
        commentLambdaQueryWrapper.eq(Comment::getLevel, 2);
        commentLambdaQueryWrapper.eq(Comment::getArticleId, comment.getArticleId());
        return commentMapper.selectList(commentLambdaQueryWrapper);
    }

    private CommentVo transferToCommentVo(Comment comment){
        CommentVo commentVo = new CommentVo();
        BeanUtils.copyProperties(comment, commentVo);
        if(comment.getLevel()==1){
            commentVo.setChildren(transferToCommentVoList(getChildrenComment(comment)));
        }
        return commentVo;
    }

    private List<CommentVo> transferToCommentVoList(List<Comment> comments){
        List<CommentVo> commentVoList = new ArrayList<>();
        for(Comment comment:comments) commentVoList.add(transferToCommentVo(comment));
        return commentVoList;
    }

    @Override
    public void addComment(Comment comment) {
        comment.setCreateDate(System.currentTimeMillis());
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentVo> getArticleCommentVo(Long articleId, PageParam pageParam) {
        Page<Comment> commentPage = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        LambdaQueryWrapper<Comment> commentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        commentLambdaQueryWrapper.eq(Comment::getArticleId, articleId);
        commentLambdaQueryWrapper.eq(Comment::getLevel, 1);
        commentLambdaQueryWrapper.orderByDesc(Comment::getCreateDate);
        IPage<Comment> commentIPage = commentMapper.selectPage(commentPage, commentLambdaQueryWrapper);
        List<Comment> comments = commentIPage.getRecords();

        return transferToCommentVoList(comments);
    }
}