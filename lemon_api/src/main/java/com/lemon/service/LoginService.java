package com.lemon.service;

import com.lemon.vo.Result;
import com.lemon.vo.param.LoginParam;

public interface LoginService {
    Result login(LoginParam loginParam);

    Result logout(String token);

    Result register(LoginParam loginParam);
}
