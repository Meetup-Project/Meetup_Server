package com.work.meetup.controller;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import com.work.meetup.dto.SignupRequest;
import com.work.meetup.dto.LoginRequest;
import com.work.meetup.dto.TokenResponse;
import com.work.meetup.service.AuthService;
import com.work.meetup.service.JwtService;
import org.springframework.http.ResponseEntity;
import java.util.Map;
import com.work.meetup.service.CustomOAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final CustomOAuth2UserService customOAuth2UserService;

    public AuthController(CustomOAuth2UserService customOAuth2UserService,JwtService jwtService, AuthService authService) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.jwtService = jwtService;
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    //  리프레시 토큰을 이용한 JWT 재발급 (POST /auth/refresh)
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }

        TokenResponse newTokens = jwtService.refreshToken(refreshToken);
        return ResponseEntity.ok(newTokens);
    }

    @PostMapping("/oauth2/login")
    public TokenResponse oauth2Login(@RequestBody OAuth2User oAuth2User) {
        return customOAuth2UserService.processOAuth2User(oAuth2User);
    }
}
