package com.lemon.service.impl;

import com.lemon.dao.mapper.CategoryMapper;
import com.lemon.dao.pojo.Category;
import com.lemon.service.CategoryService;
import com.lemon.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;

    private CategoryVo transferToCategoryVo(Category category){
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }

    private List<CategoryVo> transferToCategoryVoList(List<Category> categories){
        List<CategoryVo> categoryVoList = new ArrayList<>();
        for(Category category: categories) categoryVoList.add(transferToCategoryVo(category));
        return categoryVoList;
    }

    @Override
    public CategoryVo findCategoryVoById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        return transferToCategoryVo(category);
    }

    @Override
    public List<CategoryVo> getCategoryVos() {
        List<Category> categories = categoryMapper.selectList(null);
        return transferToCategoryVoList(categories);
    }
}
