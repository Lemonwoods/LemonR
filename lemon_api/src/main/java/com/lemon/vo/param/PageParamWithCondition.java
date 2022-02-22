package com.lemon.vo.param;

import lombok.Data;

@Data
public class PageParamWithCondition {
    private PageParam pageParam;

    private ArticleQueryCondition articleQueryCondition;

    public String getMonth(){
        if(articleQueryCondition.getMonth()!=null&& articleQueryCondition.getMonth().length()==1){
            return "0"+articleQueryCondition.getMonth();
        }
        return articleQueryCondition.getMonth();
    }
}
