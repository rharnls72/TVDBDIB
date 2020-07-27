package com.web.curation.interceptor;

import java.util.LinkedHashMap;
import java.util.Map;

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
        
        if(request.getMethod().equals("OPTIONS")) {
            return true;
        } else {

            String token = request.getHeader("jwt-token");
            // 받은 토큰이 있을 때
            if(token != null && token.length() > 0) {
                // 토큰에서 유저 정보를 빼본다
                // 이때 유효하지 않은 토큰이면 예외가 발생한다
                // 예외 발생 시 Vue 에 데이터 전달할 방법 생각!
                try {
                    // 토큰에서 유저 정보를 추출해 request 에 넣기
                    User user = new User();
                    user.setUserWithToken((Map) jwtService.getInfo(token).get("User"));
                    System.out.println(user);
                    request.setAttribute("User", user);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return false;
                }
    
                return true;
            }
            // 받은 토큰이 없을 때
            else {
                System.out.println("받은 토큰이 없습니다.");
                System.out.println("Request URL: " + request.getRequestURL());
                return false;
            }
        }
    }
}