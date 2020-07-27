package com.web.curation.service;

import java.util.Map;

import com.web.curation.model.user.User;

public interface JWTService {
    public String makeToken(User user);
    public Map<String, Object> getInfo(String token) throws Exception;
}