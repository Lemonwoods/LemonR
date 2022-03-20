package com.lemon.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class MessageVo  implements Serializable {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private String content;

    private Long createDate;

    private boolean read;
}
