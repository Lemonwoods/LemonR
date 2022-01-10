package com.lemon.service;

import com.lemon.dao.pojo.User;

public interface TokenService {
    User checkToken(String token);
}
