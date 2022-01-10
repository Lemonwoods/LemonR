package com.lemon.controller;

import com.lemon.service.UserService;
import com.lemon.vo.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("users")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("info/{id}")
    public Result getUserInfo(@RequestHeader(value = "Authorization", defaultValue = "")String token,
                              @PathVariable("id") Long id){
        return userService.findUserById(id, token);
    }
}
