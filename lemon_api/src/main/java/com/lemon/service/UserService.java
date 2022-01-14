package com.lemon.service;

import com.lemon.dao.pojo.User;
import com.lemon.vo.Result;
import com.lemon.vo.UserVo;

public interface UserService {
    User isPassWordCorrect(String account, String password); //检查用户名与密码是否对应, 如果正确, 则返回User对象, 否则返回null

    User findUserByAccount(String account);

    void save(User user);

    UserVo findUserVoById(Long id, String token);

    UserVo findUserVoById(Long id,boolean deletePrivacy);

    void updateUserInfo(User user);

    User generateInitialUser();

    void followUser(Long userId);

    void cancelFollowUser(Long userId);

}
