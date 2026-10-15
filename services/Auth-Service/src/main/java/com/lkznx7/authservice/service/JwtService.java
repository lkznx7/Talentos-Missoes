package com.lkznx7.authservice.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.crypto.SecretKey;

public interface JwtService extends UserDetailsService {

    String generateToken(String username);

    SecretKey signin();

    Claims parseToken(String token);

    boolean validateToken(String token);


}
