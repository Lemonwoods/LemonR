package com.lemon.vo;

import lombok.Data;

@Data
public class UserVo {
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

    private Long createCount;
}
