package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lemon.dao.dos.Follow;
import com.lemon.dao.mapper.FollowMapper;
import com.lemon.service.FollowService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.param.PageParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public List<Long> getFollowUserIdList(Long userId, PageParam pageParam) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Follow::getToUserId);
        queryWrapper.eq(Follow::getFromUserId, userId);
        Page<Follow> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        Page<Follow> followPage = followMapper.selectPage(page, queryWrapper);
        List<Follow> follows = followPage.getRecords();

        List<Long> userIdList = new LinkedList<>();
        for(Follow follow:follows) userIdList.add(follow.getToUserId());
        return userIdList;
    }

    @Override
    public List<Long> getFanUserIdList(Long userId, PageParam pageParam) {
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Follow::getFromUserId);
        queryWrapper.eq(Follow::getToUserId, userId);
        Page<Follow> page = new Page<>(pageParam.getPage(), pageParam.getPageSize());
        Page<Follow> followPage = followMapper.selectPage(page, queryWrapper);
        List<Follow> follows = followPage.getRecords();

        List<Long> userIdList = new LinkedList<>();
        for(Follow follow:follows) userIdList.add(follow.getFromUserId());
        return userIdList;
    }
}
