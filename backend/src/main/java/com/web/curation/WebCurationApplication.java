package com.web.curation;

import com.web.curation.interceptor.JWTInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebCurationApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(WebCurationApplication.class, args);
	}

	@Autowired
	private JWTInterceptor jwtInterceptor;

	// Interceptor 설치
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/**")
			.excludePathPatterns("/account/**");
	}

	// 전역의 CORS 처리
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.allowedHeaders("*")
			.exposedHeaders("jwt-token");
	}
}
