package com.imooc.miaosha.config;

import com.imooc.miaosha.access.AccessInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Created by lenovo on 2019/10/28.
 */
@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Autowired
    private AccessInterceptor accessInterceptor;

    /**
     * 加入自定义解析器。
     */
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
         argumentResolvers.add(userArgumentResolver);
    }

    /**
     * 拦截器配置
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(accessInterceptor);
    }

    /**
     * 静态资源配置
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //重写这个方法，映射静态资源文件
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        super.addResourceHandlers(registry);
    }



}
