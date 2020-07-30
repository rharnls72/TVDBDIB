package com.web.curation;

import com.web.curation.interceptor.JWTInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class WebCurationApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(WebCurationApplication.class, args);
	}

	@Autowired
	private JWTInterceptor jwtInterceptor;

	// 인터셉터를 거치지 않고 요청을 보낼 수 있는 경로 설정
	private static final String[] EXCLUDE_PATHS = {
		"/account/**",
		"/test/**"
	};

	
	// Interceptor 설치
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
			.addPathPatterns("/**") // 모든 요청 인터셉트
			.excludePathPatterns(EXCLUDE_PATHS); // 인터셉트 하지 않을 경로 설정
	}
	

	// 전역의 CORS 처리
	/*
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("*")
			.allowedMethods("*")
			.allowedHeaders("*");
			// .exposedHeaders("jwt-token");
	}
	*/
}
