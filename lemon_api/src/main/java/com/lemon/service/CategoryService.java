package com.lemon.service;

import com.lemon.vo.CategoryVo;

import java.util.List;

public interface CategoryService {
    CategoryVo findCategoryVoById(Long categoryId);

    List<CategoryVo> getCategoryVos();
}
