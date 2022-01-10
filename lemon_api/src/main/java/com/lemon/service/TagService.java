package com.lemon.service;

import com.lemon.vo.TagVo;

import java.util.List;

public interface TagService {
    List<TagVo> findTagVoListByArticleId(Long id);
}
