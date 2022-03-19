package com.lemon.utils.aop;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
@Component
@Slf4j
public class LogApiAspect {

    @Pointcut("@annotation(com.lemon.utils.Log)")
    public void log(){

    }

    @Around("log()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        long curTime = System.currentTimeMillis();

        Signature signature = joinPoint.getSignature();
        Class type = signature.getDeclaringType();
        String typeSimpleName= type.getSimpleName();
        String methodName = signature.getName();

        Object[] args = joinPoint.getArgs();
        Class[] argsClasses = new Class[args.length];
        for(int i=0;i<args.length;i++){
            argsClasses[i] = args[i].getClass();
        }
        Method method = type.getMethod(methodName, argsClasses);
        Parameter[] parameters = method.getParameters();

        StringBuffer stringBuffer = new StringBuffer();
        for(int i = 0;i<args.length;i++){
            stringBuffer.append(parameters[i].getName())
                    .append(":")
                    .append(args[i])
                    .append(",");
        }
        if(stringBuffer.length()>0) stringBuffer.deleteCharAt(stringBuffer.length()-1);

        Object res;
        try{
            res = joinPoint.proceed();
            log.info("调用{}.{}成功, 参数列表为:[{}], 返回值为:[{}]", typeSimpleName, methodName, stringBuffer.toString(), JSONObject.toJSONString(res));

        }catch (Exception e){
            log.error("调用{}.{}失败", typeSimpleName, methodName);
            throw e;
        } finally {
            log.info("调用{}.{}方法，耗时{}ms", typeSimpleName, methodName, (System.currentTimeMillis() - curTime));
        }
        return res;
    }
}
