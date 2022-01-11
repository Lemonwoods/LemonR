package com.lemon.controller;

import com.lemon.dao.pojo.User;
import com.lemon.service.UserService;
import com.lemon.utils.UserThreadLocal;
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
        return Result.succeed(userService.findUserVoById(id, token));
    }

    @PostMapping("info/current")
    public Result getCurrentUserInfo(){
        return Result.succeed(userService.findUserVoById(UserThreadLocal.get().getId(), false));
    }

    @PostMapping("info/change")
    public Result changeUserInfo(@RequestBody User user){
        user.setId(UserThreadLocal.get().getId());
        userService.updateUserInfo(user);
        return Result.succeed(null);
    }
}
