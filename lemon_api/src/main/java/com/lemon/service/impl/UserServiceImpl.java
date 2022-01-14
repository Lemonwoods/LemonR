package com.lemon.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lemon.dao.mapper.UserMapper;
import com.lemon.dao.pojo.User;
import com.lemon.service.FollowService;
import com.lemon.service.TokenService;
import com.lemon.service.UserService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.Result;
import com.lemon.vo.UserCountField;
import com.lemon.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private TokenService tokenService;

    @Resource
    private FollowService followService;

    private void deletePrivacy(User user){
        user.setEmail(null);
        user.setMobilePhoneNumber(null);
    }

    private UserVo tranferToUserVo(User user){
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        return userVo;
    }

    private void changeUserCountField(Long userId, UserCountField userCountField, Integer change){
        User user = userMapper.selectById(userId);
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", userId);
        updateWrapper.set(userCountField.getFieldName(), getFieldCountValue(user, userCountField)+change);
        userMapper.update(null, updateWrapper);
    }

    private int getFieldCountValue(User user, UserCountField userCountField){
        if(userCountField.getFieldName().equals("fan_count")) return user.getFanCount();
        if(userCountField.getFieldName().equals("follow_count")) return user.getFollowCount();
        //todo 改进
        return 0;
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
    public UserVo findUserVoById(Long id, String token) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userMapper.selectOne(queryWrapper);

        User userToken = tokenService.checkToken(token);

        if(userToken==null||!userToken.getId().equals(id)){
            deletePrivacy(user);
        }

        return tranferToUserVo(user);
    }

    @Override
    public UserVo findUserVoById(Long id,boolean deletePrivacy) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, id);
        User user = userMapper.selectOne(queryWrapper);

        if(deletePrivacy){
            deletePrivacy(user);
        }

        return tranferToUserVo(user);
    }

    @Override
    public void updateUserInfo(User user) {
        userMapper.updateById(user);
    }

    @Override
    public User generateInitialUser() {
        User user = new User();
        user.setFanCount(0);
        user.setFollowCount(0);
        return user;
    }

    private void addFollowCount(Long userId) {
        changeUserCountField(userId, UserCountField.FOLLOW_COUNT, 1);
    }

    private void subFollowCount(Long userId) {
        changeUserCountField(userId, UserCountField.FOLLOW_COUNT, -1);
    }

    private void addFanCount(Long userId) {
        changeUserCountField(userId, UserCountField.FAN_COUNT, 1);
    }

    private void subFanCount(Long userId) {
        changeUserCountField(userId, UserCountField.FAN_COUNT, -1);
    }

    @Override
    @Transactional
    public void followUser(Long userId) {
        //todo 增加检查:是否订阅自己, 是否已经订阅过了
        addFanCount(userId);
        addFollowCount(UserThreadLocal.get().getId());
        followService.followUser(userId);
    }

    @Override
    @Transactional
    public void cancelFollowUser(Long userId) {
        subFanCount(userId);
        subFollowCount(UserThreadLocal.get().getId());
        followService.cancelFollowUser(userId);
    }
}
