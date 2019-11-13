package com.imooc;

import javafx.application.Application;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
//指定要变成实现类的接口所在的包，然后包下面的所有接口在编译之后都会生成相应的实现类
@MapperScan("com.imooc.miaosha.*.dao")
public class MiaoshaIdeaApplication{



	public static void main(String[] args) {
		SpringApplication.run(MiaoshaIdeaApplication.class, args);
	}

}
