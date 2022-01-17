package com.lemon.vo;

import lombok.Data;

@Data
public class MessageVo {
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private String content;

    private Long createDate;

    private boolean read;
}
