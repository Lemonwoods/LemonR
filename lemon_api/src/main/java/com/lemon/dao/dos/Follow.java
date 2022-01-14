package com.lemon.dao.dos;

import lombok.Data;

@Data
public class Follow {
    private Long id;

    private Long fromUid;

    private Long toUid;

    private Long createDate;
}
