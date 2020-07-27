package com.web.curation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.curation.model.user.User;
import com.web.curation.service.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JWTInterceptor implements HandlerInterceptor {
    
    @Autowired
    private JWTService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        String token = request.getHeader("jwt-token");
        // 받은 토큰이 있을 때
        if(token != null && token.length() > 0) {
            // 토큰에서 유저 정보를 빼본다
            // 이때 유효하지 않은 토큰이면 예외가 발생한다
            // 예외 발생 시 Vue 에 데이터 전달할 방법 생각!
            try {
                User user = (User) jwtService.getInfo(token).get("User");
                System.out.println(user);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        // 받은 토큰이 없을 때
        else {
            System.out.println("받은 토큰이 없습니다.");
        }

        return true;
    }
}