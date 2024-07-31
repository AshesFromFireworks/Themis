package com.themis.MyMvcConfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/index").setViewName("forward:/index.html");

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginHandlerInterceptor()).excludePathPatterns(
                "/", "/login", "/dp/register", "/in/register", "/css/**", "/image/**", "/index", "/UploadFile",
                "/static/js/**", "/favicon.ico", "/dp/register", "/index.html", "/dp/credit", "/in/credit",
                "/download", "/admin/login", "/load/*"
        );
    }
}
