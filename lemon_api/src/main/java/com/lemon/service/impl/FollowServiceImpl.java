package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lemon.dao.dos.Follow;
import com.lemon.dao.mapper.FollowMapper;
import com.lemon.service.FollowService;
import com.lemon.utils.UserThreadLocal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class FollowServiceImpl implements FollowService {
    @Resource
    private FollowMapper followMapper;

    @Override
    @Transactional
    public void followUser(Long userId) {
        Follow follow = new Follow();
        follow.setFromUserId(UserThreadLocal.get().getId());
        follow.setToUserId(userId);
        follow.setCreateDate(System.currentTimeMillis());
        followMapper.insert(follow);
    }

    @Override
    @Transactional
    public void cancelFollowUser(Long userId) {
        UpdateWrapper<Follow> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("fromUid", UserThreadLocal.get().getId());
        updateWrapper.eq("toUid", userId);
        followMapper.delete(updateWrapper);
    }
}
