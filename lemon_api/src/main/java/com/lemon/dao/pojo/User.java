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

    private Integer fan_count;

    private Integer follow_count;

    private Long create_date;

    private String password;

    private String salt;

    private Boolean admin;

    private String status;

    private Long last_login;

    private Boolean deleted;
}
