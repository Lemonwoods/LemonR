package com.lemon.controller;

import com.lemon.dao.pojo.Comment;
import com.lemon.service.CommentService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.CommentVo;
import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("comments")
public class CommentController {
    @Resource
    private CommentService commentService;

    @PostMapping("add")
    public Result addComments(@RequestBody Comment comment){
        comment.setFromUid(UserThreadLocal.get().getId());
        CommentVo commentVo = commentService.addComment(comment);
        return Result.succeed(commentVo);
    }

    @PostMapping("articles/{articleId}")
    public Result getArticleComments(@PathVariable("articleId")Long articleId,
                                     @RequestBody PageParam pageParam){
        List<CommentVo> commentVoList = commentService.getArticleCommentVo(articleId, pageParam);
        return Result.succeed(commentVoList);
    }
}
