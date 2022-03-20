package com.lemon.utils.aop;

import com.lemon.utils.FuzzyCacheEvict;
import com.lemon.utils.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Set;

@Aspect
@Component
public class FuzzyCacheEvictAspect {
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Pointcut("@annotation(com.lemon.utils.FuzzyCacheEvict)")
    public void fuzzyCacheEvict(){};

    @AfterReturning(value = "fuzzyCacheEvict()")
    public void process(JoinPoint joinPoint){
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        FuzzyCacheEvict annotation = method.getAnnotation(FuzzyCacheEvict.class);
        String value = annotation.value();
        String[] keys = annotation.key();

        //  解析Spel
        EvaluationContext evaluationContext = new StandardEvaluationContext();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();

        for(int i = 0;i<parameters.length;i++){
            evaluationContext.setVariable(parameters[i].getName(), args[i]);
        }

        ExpressionParser expressionParser = new SpelExpressionParser();
        String[] newKeys = new String[keys.length];
        for(int i = 0;i<keys.length;i++){
            Expression keyExpression = expressionParser.parseExpression(keys[i]);
            String newKey = keyExpression.getValue(evaluationContext, String.class);
            newKeys[i] = newKey;
        }

        if(!value.equals("")&&newKeys.length==0){
            cleanRedisCache(value+"*");
        }else{
            for(String key:newKeys){
                cleanRedisCache("*"+value+"::"+key+"*");
            }
        }

    }

    @Log
    private void cleanRedisCache(String key) {
        if (key != null) {
            Set<String> stringSet = redisTemplate.keys(key);
            redisTemplate.delete(stringSet);
        }
    }
}
