package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemon.dao.mapper.ArticleTagMapper;
import com.lemon.dao.mapper.TagMapper;
import com.lemon.dao.pojo.ArticleTag;
import com.lemon.dao.pojo.Tag;
import com.lemon.service.TagService;
import com.lemon.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagMapper tagMapper;

    @Resource
    private ArticleTagMapper articleTagMapper;

    private TagVo transferToTagVo(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag, tagVo);
        return tagVo;
    }

    private List<TagVo> transferToTagVoList(List<Tag> tags){
        List<TagVo> tagVos = new ArrayList<>(tags.size());
        for(Tag tag:tags) tagVos.add(transferToTagVo(tag));
        return tagVos;
    }

    @Override
    public List<TagVo> findTagVoListByArticleId(Long id) {
        LambdaQueryWrapper<ArticleTag> articleTagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        articleTagLambdaQueryWrapper.eq(ArticleTag::getArticleId, id);
        List<ArticleTag> articleTags = articleTagMapper.selectList(articleTagLambdaQueryWrapper);
        List<Long> tagIdList = new ArrayList<>();
        for(ArticleTag articleTag:articleTags) tagIdList.add(articleTag.getTagId());

        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.in(Tag::getId, tagIdList);
        List<Tag> tags = tagMapper.selectList(tagLambdaQueryWrapper);

        return transferToTagVoList(tags);
    }
}
