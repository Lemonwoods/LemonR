package com.lemon.dao.dos;

import lombok.Data;

@Data
public class Follow {
    private Long id;

    private Long fromUserId;

    private Long toUserId;

    private Long createDate;
}
