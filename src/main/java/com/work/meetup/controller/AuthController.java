package com.work.meetup.controller;

import com.work.meetup.dto.LoginRequest;
import com.work.meetup.dto.SignupRequest;
import com.work.meetup.dto.TokenResponse;
import com.work.meetup.security.CustomOAuth2User;
import com.work.meetup.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //  자체 회원가입
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest request) {
        authService.registerUser(request.getEmail(), request.getPassword(), request.getName());
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    //  자체 로그인 (이메일 & 비밀번호)
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@RequestBody LoginRequest request, HttpServletResponse response) {
        TokenResponse tokenResponse = authService.login(request);
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refreshAccessToken(@RequestHeader("Authorization") String refreshToken) {
        TokenResponse tokenResponse = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(tokenResponse);
    }

    //  OAuth 로그인 후 사용자 정보 반환 (JWT 포함)
    @GetMapping("/oauth2/user")
    public ResponseEntity<?> getOAuthUser(@AuthenticationPrincipal CustomOAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return ResponseEntity.badRequest().body("OAuth 인증 정보가 없습니다.");
        }
        return ResponseEntity.ok(oAuth2User);
    }

    //  OAuth 로그인 후 JWT 발급 (Next.js 프론트에서 호출)
    @GetMapping("/oauth2/callback")
    public ResponseEntity<TokenResponse> oauthCallback(OAuth2AuthenticationToken authToken) {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authToken.getPrincipal();
        TokenResponse tokenResponse = new TokenResponse(oAuth2User.getToken(), null);
        return ResponseEntity.ok(tokenResponse);
    }
}
