package com.lemon.controller;

import com.lemon.service.CategoryService;
import com.lemon.vo.CategoryVo;
import com.lemon.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("categories")
public class CategoryController {
    @Resource
    private CategoryService categoryService;


    @GetMapping
    public Result getCategories(){
        List<CategoryVo> categoryVoList = categoryService.getCategoryVos();
        return Result.succeed(categoryVoList);
    }
}
