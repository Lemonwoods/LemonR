package com.lemon.controller;

import com.lemon.service.TagService;
import com.lemon.vo.Result;
import com.lemon.vo.TagVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {
    @Resource
    private TagService tagService;

    @GetMapping
    public Result getTags(){
        List<TagVo> tagVoList = tagService.getTagVos();
        return Result.succeed(tagVoList);
    }
}
