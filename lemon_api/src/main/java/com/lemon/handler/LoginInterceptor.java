package com.lemon.handler;

import com.lemon.dao.pojo.User;
import com.lemon.utils.UserThreadLocal;
import com.lemon.vo.ErrorCode;
import com.lemon.vo.Result;

import com.alibaba.fastjson.JSON;
import com.lemon.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    TokenService tokenService;

    private void getFailResponse(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        Result result = Result.fail(ErrorCode.NO_LOGIN.getCode(), ErrorCode.NO_LOGIN.getMsg());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().println(JSON.toJSONString(result));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)){ // 对访问资源的操作不进行拦截。
            return true;
        }

        //对token进行判断，先判断是否为空，在判断是否合法
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            getFailResponse(ErrorCode.NO_LOGIN, response);
            return false;
        }

        User user = tokenService.checkToken(token);

        if(user == null){
            getFailResponse(ErrorCode.NO_LOGIN, response);
            return false;
        }

        UserThreadLocal.put(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserThreadLocal.remove();
    }
}
