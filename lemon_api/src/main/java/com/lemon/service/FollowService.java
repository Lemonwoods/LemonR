package com.lemon.service;

import com.lemon.vo.param.PageParam;

import java.util.List;

public interface FollowService {
    void followUser(Long userId);

    void cancelFollowUser(Long userId);

    List<Long> getFollowUserIdList(Long userId, PageParam pageParam);

    List<Long> getFanUserIdList(Long userId, PageParam pageParam);
}
