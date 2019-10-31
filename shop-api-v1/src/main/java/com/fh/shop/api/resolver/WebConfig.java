package com.fh.shop.api.resolver;

import com.fh.shop.api.commons.MemberVoArgumentResolver;
import com.fh.shop.api.interceptor.IdemInterceptor;
import com.fh.shop.api.interceptor.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    public void addInterceptors(org.springframework.web.servlet.config.annotation.InterceptorRegistry registry) {
        registry.addInterceptor(getInter()).addPathPatterns("/**");
        registry.addInterceptor(getIdemInter()).addPathPatterns("/**");
    }

    public void addArgumentResolvers(java.util.List<org.springframework.web.method.support.HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(getMemberVo());
    }

    @Bean
    public Interceptor getInter(){
        return new Interceptor();
    }

    @Bean
    public IdemInterceptor getIdemInter(){
        return new IdemInterceptor();
    }

    @Bean
    public MemberVoArgumentResolver getMemberVo(){
        return new MemberVoArgumentResolver();
    }
}
