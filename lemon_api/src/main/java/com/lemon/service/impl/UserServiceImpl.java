package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemon.dao.mapper.UserMapper;
import com.lemon.dao.pojo.User;
import com.lemon.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User isPassWordCorrect(String account, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        queryWrapper.eq(User::getPassword, password);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public User findUserByAccount(String account) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, account);
        User user = userMapper.selectOne(queryWrapper);
        return user;
    }

    @Override
    public void save(User user) {
        userMapper.insert(user);
    }
}
