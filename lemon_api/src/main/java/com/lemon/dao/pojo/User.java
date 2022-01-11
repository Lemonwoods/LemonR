package com.lemon.dao.pojo;

import lombok.Data;

@Data
public class User {
    private Long id;

    private String account;

    private String nickname;

    private String userDescription;

    private String coverImg;

    private String avatar;

    private String email;

    private String mobilePhoneNumber;

    private Integer fanCount;

    private Integer followCount;

    private Long createDate;

    private String password;

    private String salt;

    private Boolean admin;

    private String status;

    private Long lastLogin;

    private Boolean deleted;
}
