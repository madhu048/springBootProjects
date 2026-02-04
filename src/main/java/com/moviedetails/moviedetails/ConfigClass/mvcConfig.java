package com.moviedetails.moviedetails.ConfigClass;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.moviedetails.moviedetails.Intercepter.NoCacheIntercepter;

@Configuration
public class mvcConfig implements  WebMvcConfigurer // adding  NoCacheIntercepter to the WebMvcConfig
{
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NoCacheIntercepter()).addPathPatterns("/**");
	}
}