package com.lemon.service.impl;

import com.alibaba.fastjson.JSON;
import com.lemon.dao.pojo.User;
import com.lemon.service.TokenService;
import com.lemon.utils.JWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    RedisTemplate<String, String> redisTemplate;

    @Override
    public User checkToken(String token) {
        if(StringUtils.isBlank(token)){
            return null;
        }

        Map<String, Object> stringObjectMap = JWTUtils.checkToken(token);
        if(stringObjectMap == null){
            return null;
        }

        String userJson = redisTemplate.opsForValue().get("TOKEN_"+token);
        if(StringUtils.isBlank(userJson)){
            return null;
        }
        User user = JSON.parseObject(userJson, User.class);
        return user;
    }
}
