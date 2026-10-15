package com.lkznx7.authservice.service;

import com.lkznx7.authservice.config.JwtProperties;
import com.lkznx7.authservice.repository.UserAuthRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService {

    UserAuthRepository userAuth;
    JwtProperties jwtProperties;
    IpAdressService ipAdressService;

    public JwtServiceImpl(UserAuthRepository userAuth, JwtProperties jwtProperties, IpAdressService ipAdressService) {
        this.userAuth = userAuth;
        this.jwtProperties = jwtProperties;
        this.ipAdressService = ipAdressService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuth.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public String generateToken(String username) {
        UserDetails user = loadUserByUsername(username);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(new Date(
                        System.currentTimeMillis()
                                + (jwtProperties.getExpiration() * 1000L)
                ))
                .claim(
                        "ROLES_",
                        user.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .toList()
                )
                .signWith(signin())
                .compact();
    }
    public SecretKey signin() {
        byte[] secretBytes = Base64.getDecoder()
                .decode(jwtProperties.getSecret());
        return new SecretKeySpec(secretBytes, "HmacSHA256");
    }

    public Claims parseToken(String token) {
        return Jwts.parser().verifyWith(signin()).build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // fiz sem IA
}
