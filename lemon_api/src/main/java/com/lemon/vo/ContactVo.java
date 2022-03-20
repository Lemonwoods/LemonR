package com.lemon.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class ContactVo  implements Serializable {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String avatar;

    private String nickName;

    private MessageVo lastMessage;
}
