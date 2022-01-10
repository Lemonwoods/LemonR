package com.lemon.vo.param;

import lombok.Data;

@Data
public class LoginParam {
    private String account; //用户名

    private String password; //用户密码

    private String nickname; //用户昵称
}
