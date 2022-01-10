package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lemon.dao.mapper.UserMapper;
import com.lemon.dao.pojo.User;
import com.lemon.service.TokenService;
import com.lemon.service.UserService;
import com.lemon.vo.Result;
import com.lemon.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenService tokenService;

    private void deletePrivacy(User user){
        user.setEmail(null);
        user.setMobilePhoneNumber(null);
    }

    private UserVo tranferToUserVo(User user){
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

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

    @Override
    public Result findUserById(Long id, String token) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userMapper.selectOne(queryWrapper);

        User userToken = tokenService.checkToken(token);

        if(userToken==null||!userToken.getId().equals(id)){
            deletePrivacy(user);
        }

        return Result.succeed(tranferToUserVo(user));
    }

    @Override
    public void updateUserInfo(User user) {
        userMapper.updateById(user);
    }
}
