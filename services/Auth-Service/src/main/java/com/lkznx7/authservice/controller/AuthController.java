package com.lkznx7.authservice.controller;


import com.lkznx7.authservice.dto.request.LoginUser;
import com.lkznx7.authservice.dto.request.RegisterUser;
import com.lkznx7.authservice.entity.UserAuth;
import com.lkznx7.authservice.repository.UserAuthRepository;
import com.lkznx7.authservice.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    JwtService jwtService;
    PasswordEncoder passwordEncoder;

    AuthenticationManager authenticationManager;
    UserAuthRepository userAuthRepository;
    public AuthController(JwtService jwtService, PasswordEncoder passwordEncoder , AuthenticationManager authenticationManager, UserAuthRepository userAuthRepository) {
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userAuthRepository = userAuthRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> Register (@RequestBody RegisterUser registerUser){
        try {
            UserAuth userAuth = new UserAuth(registerUser.username(), passwordEncoder.encode(registerUser.password()));
            userAuthRepository.save(userAuth);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            throw e;
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login (@RequestBody LoginUser loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.username(),
                        loginUser.password()
                )
        );
        String token = jwtService.generateToken(authentication.getName());
        return ResponseEntity.ok().build();
    }
}
