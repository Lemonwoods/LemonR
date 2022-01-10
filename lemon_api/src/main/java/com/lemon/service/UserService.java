package com.lemon.service;

import com.lemon.dao.pojo.User;

public interface UserService {
    User isPassWordCorrect(String account, String password); //检查用户名与密码是否对应, 如果正确, 则返回User对象, 否则返回null

    User findUserByAccount(String account);

    void save(User user);
}
