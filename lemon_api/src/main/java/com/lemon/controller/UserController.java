package com.lemon.controller;

import com.lemon.dao.pojo.User;
import com.lemon.service.FollowService;
import com.lemon.service.UserService;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.Result;
import com.lemon.vo.param.PageParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private FollowService followService;

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

    @PostMapping("follow/{userId}")
    public Result followUser(@PathVariable("userId")Long userId){
        userService.followUser(userId);
        return Result.succeed(null);
    }

    @PostMapping("cancelFollow/{userId}")
    public Result cancelFollowUser(@PathVariable("userId")Long userId){
        userService.cancelFollowUser(userId);
        return Result.succeed(null);
    }

    @PostMapping("{id}/followList")
    public Result getFollowList(@PathVariable("id")Long userId,
                                @RequestBody PageParam pageParam){
        List<Long> userIdList = followService.getFollowUserIdList(userId, pageParam);
        return Result.succeed(userIdList);
    }

    @PostMapping("{id}/fanList")
    public Result getFanList(@PathVariable("id")Long userId,
                                @RequestBody PageParam pageParam){
        List<Long> userIdList = followService.getFanUserIdList(userId, pageParam);
        return Result.succeed(userIdList);
    }
}
