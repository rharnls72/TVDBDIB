package com.web.curation.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import com.web.curation.model.user.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
출처: https://goodteacher.tistory.com/98?category=763707
출처2: https://alwayspr.tistory.com/8
*/
@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.expmin}")
    private Long expireMin;

    @Override
    public String makeToken(User user) {
        final JwtBuilder builder = Jwts.builder();

        // 헤더 설정
        builder.setHeaderParam("type", "JWT");

        // 현재 로그인에서만 토큰 사용
        String token = builder.setSubject("로그인 토큰")
        // 토큰 유효기간 설정
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * expireMin))
        // 토큰에 담을 정보 설정
            .claim("User", user)
        // 토큰 암호화
            .signWith(SignatureAlgorithm.HS256, generateKey())
        // 토큰 직렬화
            .compact();
        
        return token;
    }

    @Override
    public Map<String, Object> getInfo(String token) throws Exception {
        Jws<Claims> claims = null;
        try {
			claims = Jwts.parser()
						 .setSigningKey(generateKey())
						 .parseClaimsJws(token);
		} catch (Exception e) {
			throw new Exception("계정 정보가 유효하지 않습니다. 다시 로그인 해 주십시오.");
		}
		return claims.getBody();
    }

    private byte[] generateKey(){
		byte[] key = null;
		try {
			key = salt.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Making JWT Key Error : " + e.getMessage());
		}
		
		return key;
	}
}