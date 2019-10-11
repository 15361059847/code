package com.immoc.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 跨域配置
 */

/*
* @configuration用于定义配置类，可替换xml配置文件。被注解的类内部包含一个或者多个被@Bean注解的方法，这些方法将会被
* AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化
* Spring容器。
* 注意：@Configruation注解的配置类有如下要求
*   1、@Configruation不可以是final类型
*   2、@Configuration不可以是匿名类
*   3、嵌套的configuration必须是静态类
*
*   一、用@Configuration加载spring
*   1.1、@Configuration配置spring并启动spring容器
*   1.2、@Configuration启动容器 + @Bean注册Bean
*   1.3、@Configuration启动容器 + @Component注册Bean
*   1.4、使用AnnotationConfigApplicationContext注册AppContext类的两种方法
*   1.5、配置Web应用程序(web.xml中配置AnnotationConfigApplicationContext)
*
*
*   二、组合多个配置类
*   2.1、在@Configuration中引入spring的xml配置文件
*   2.2、在@Configuration中引入其他注解配置
*   2.3、@Configuration嵌套（嵌套是的Configuration必须是静态类）
*
*   三、@EnableXXX注解
*   四、@Profile逻辑组配置
*   五、使用外部变量
*
* */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setMaxAge(300l);

        source.registerCorsConfiguration("/**",config);
        return new CorsFilter();
    }
}
