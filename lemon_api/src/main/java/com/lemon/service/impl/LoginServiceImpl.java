package com.lemon.service.impl;

import com.alibaba.fastjson.JSON;
import com.lemon.dao.pojo.User;
import com.lemon.service.LoginService;
import com.lemon.service.UserService;
import com.lemon.utils.JWTUtils;
import com.lemon.utils.SysUserUtils;
import com.lemon.vo.ErrorCode;
import com.lemon.vo.Result;
import com.lemon.vo.param.LoginParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service

public class LoginServiceImpl implements LoginService {
    private static final int duration = 1; //token在redis中的保存时间, 单位为day

    @Resource
    private UserService userService ;
    @Resource
    private RedisTemplate<String,String> redisTemplate;
    String salt = "lemon**";

    @Override
    public Result login(LoginParam loginParam) {

        /**
         * 1. 检验参数合法性
         * 2. 检验密码与数据库中的是否对应
         * 3. 如果校验正确, 则生成token, 并将token存入redis
         */

        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        if(StringUtils.isBlank(account)||StringUtils.isBlank(password)){
            return Result.fail(ErrorCode.PARAMS_ERROR.getCode(), ErrorCode.PARAMS_ERROR.getMsg());
        }

        password = DigestUtils.md5Hex(password+salt);
        User user = userService.isPassWordCorrect(account, password);

        if(user == null){
            return Result.fail(ErrorCode.ACCOUNT_PWD_NOT_EXIST.getCode(), ErrorCode.ACCOUNT_PWD_NOT_EXIST.getMsg());
        }

        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user), duration, TimeUnit.DAYS);

        return Result.succeed(token);
    }

    @Override
    public Result logout(String token) {
        redisTemplate.delete("TOKEN_"+token);
        return Result.succeed(null);
    }

    @Override
    public Result register(LoginParam loginParam) {
        String account = loginParam.getAccount();
        String password = loginParam.getPassword();
        String nickname = loginParam.getNickname();

        if(!SysUserUtils.isAccountValid(account)
                ||!SysUserUtils.isPasswordValid(password)
                ||!SysUserUtils.isNickName(nickname)){
            return Result.fail(ErrorCode.FORMAT_ERROR.getCode(), ErrorCode.FORMAT_ERROR.getMsg());
        }

        User user = userService.findUserByAccount(account);
        if(user!=null){
            return Result.fail(ErrorCode.ACCOUNT_EXIST.getCode(), ErrorCode.ACCOUNT_EXIST.getMsg());
        }

        user = new User();
        user.setAccount(account);
        user.setNickname(nickname);
        user.setPassword(DigestUtils.md5Hex(password+salt));

        userService.save(user);

        String token = JWTUtils.createToken(user.getId());
        redisTemplate.opsForValue().set("TOKEN_"+token, JSON.toJSONString(user), 1, TimeUnit.DAYS);
        return Result.succeed(token);
    }
}
