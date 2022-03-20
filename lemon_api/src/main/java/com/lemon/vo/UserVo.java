package com.lemon.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVo  implements Serializable {
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
