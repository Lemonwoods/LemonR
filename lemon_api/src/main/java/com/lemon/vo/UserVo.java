package com.lemon.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class UserVo {
    @JsonSerialize(using = ToStringSerializer.class)
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
