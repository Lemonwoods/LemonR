package com.lemon.handler;

import com.lemon.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//对加了@Controller注解的方法进行拦截处理， AOP实现
@ControllerAdvice
public class AllExceptionHandler {

    //进行异常处理
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result doException(Exception ex){
        ex.printStackTrace();
        return Result.fail(-1, "后端系统异常");
    }
}
