package com.lemon.controller;

import com.lemon.dao.pojo.User;
import com.lemon.service.UserService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.Result;
import com.lemon.vo.UserVo;
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

    @PostMapping("info/change")
    public Result changeUserInfo(@RequestBody User user){
        user.setId(UserThreadLocal.get().getId());
        userService.updateUserInfo(user);
        return Result.succeed(null);
    }
}
