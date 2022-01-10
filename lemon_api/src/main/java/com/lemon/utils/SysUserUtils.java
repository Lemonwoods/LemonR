package com.lemon.utils;

import org.apache.commons.lang3.StringUtils;

public class SysUserUtils {
    public static boolean isAccountValid(String account){
        if (StringUtils.isBlank(account)) return false;
        return true;
    }

    public static boolean isPasswordValid(String password){
        if(StringUtils.isBlank(password)) return false;
        return true;
    }

    public static boolean isNickName(String nickname){
        if(StringUtils.isBlank(nickname)) return false;
        return true;
    }
}
