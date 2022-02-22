package com.lemon.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

@Data
public class ContactVo {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String avatar;

    private String nickName;

    private MessageVo lastMessage;
}
