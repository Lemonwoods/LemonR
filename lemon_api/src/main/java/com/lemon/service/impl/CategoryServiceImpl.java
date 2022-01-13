package com.lemon.service.impl;

import com.lemon.dao.mapper.CategoryMapper;
import com.lemon.dao.pojo.Category;
import com.lemon.service.CategoryService;
import com.lemon.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    private CategoryVo transferToCategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    @Override
    public CategoryVo findCategoryVoById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return transferToCategoryVo(category);
    }
}
