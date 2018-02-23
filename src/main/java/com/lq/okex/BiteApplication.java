package com.lq.okex;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.lq.okex.dao") 
//定时器注解
@EnableScheduling
@EnableConfigurationProperties
public class BiteApplication extends SpringBootServletInitializer{ 
	 @Override
	    protected SpringApplicationBuilder configure(
	            SpringApplicationBuilder application) {
	        return application.sources(BiteApplication.class);
	    }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(BiteApplication.class, args);
	}
}
