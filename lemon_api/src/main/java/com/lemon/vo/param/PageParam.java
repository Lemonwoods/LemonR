package com.lemon.vo.param;

import lombok.Data;

@Data
public class PageParam {
    private int pageSize = 10;
    private int page = 1;
}
