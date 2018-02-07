package com.lq.bite;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.lq.bite.interceptor.IsLoginInterceptor;

/**
 * 全局配置拦截器
 * 
 * @author l.q
 *
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

	// 关键，将拦截器作为bean写入配置中
	@Bean
	public IsLoginInterceptor isLoginInterceptor() {
		return new IsLoginInterceptor();
	}


	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(isLoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/page/toLogin",
				"/account/save", "/account/checkAccount", "/page/toRegister","/user/login","/user/saveUser");
		super.addInterceptors(registry);
	}
}
