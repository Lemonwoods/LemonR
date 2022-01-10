package com.lemon.vo;

public enum ErrorCode {
    // 链接相关
    PARAMS_ERROR(1001, "参数错误"),
    FORMAT_ERROR(1002, "格式错误"),
    SESSION_TIME_OUT(1003, "会话超时"),
    UPLOAD_ERROR(1004, "上传失败"),

    // 用户相关
    ACCOUNT_PWD_NOT_EXIST(2001, "用户名或密码不存在"),
    ACCOUNT_EXIST(2002, "账户已存在"),
    NO_LOGIN(2003, "未登录"),
    TOKEN_INVALID(2004, "token不合法"),
    USER_NOT_EXIST(2005, "账户不存在"),

    // 权限相关
    NO_PERMISSION(3001, "无访问权限");

    private int code;
    private String msg;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
