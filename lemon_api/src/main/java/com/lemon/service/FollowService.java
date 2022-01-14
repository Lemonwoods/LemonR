package com.lemon.service;

public interface FollowService {
    void followUser(Long userId);

    void cancelFollowUser(Long userId);
}
