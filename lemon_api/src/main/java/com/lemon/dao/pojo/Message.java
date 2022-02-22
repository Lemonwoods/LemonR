package com.lemon.dao.pojo;

import lombok.Data;

@Data
public class Message {
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private String content;

    private Long createDate;

    private boolean hasRead;
}
