package com.lemon.controller;

import com.lemon.service.LoginService;
import com.lemon.vo.Result;
import com.lemon.vo.param.LoginParam;
import com.mysql.cj.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @PostMapping("login")
    public Result login(@RequestBody LoginParam loginParam){
        return loginService.login(loginParam);
    }

    @PostMapping("logout")
    public Result logout(@RequestHeader("Authorization")String token){
        return loginService.logout(token);
    }

    @PostMapping("register")
    public  Result register(@RequestBody LoginParam loginParam){
        return loginService.register(loginParam);
    }


}
