package com.lkznx7.authservice.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class IpAdressService {

    public String getIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
