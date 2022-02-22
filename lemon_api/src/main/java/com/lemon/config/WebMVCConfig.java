package com.lemon.config;

import com.lemon.handler.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Resource
    LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    // 拦截用户请求,判断是否登陆,如果没有登陆, 将会阻止访问
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/users/info/change")
                .addPathPatterns("/users/info/current")
                .addPathPatterns("/comments/add")
                .addPathPatterns("/articles/*/remove")
                .addPathPatterns("/articles/*/like/*")
                //.addPathPatterns("/articles/*/viewCount/add")
                .addPathPatterns("/articles/publish")
                .addPathPatterns("/users/follow/*")
                .addPathPatterns("/users/cancelFollow/*")
                .addPathPatterns("/chat/**");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/image/**").addResourceLocations("file:D:/media/test/");
    }
}
