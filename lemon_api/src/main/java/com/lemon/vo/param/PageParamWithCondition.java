package com.lemon.vo.param;

import lombok.Data;

@Data
public class PageParamWithCondition {
    private PageParam pageParam;

    private Long categoryId;
    private Long tagId;
    private Long authorId;

    private String year;
    private String month;

    public String getMonth(){
        if(month!=null&&month.length()==1){
            return "0"+month;
        }
        return month;
    }
}
