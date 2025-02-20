package com.work.meetup.service;

import org.springframework.stereotype.Service;

import com.work.meetup.config.JwtUtil;
import com.work.meetup.dto.TokenResponse;


@Service
public class JwtService {


    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public TokenResponse refreshToken(String refreshToken) {
        try {
            String email = jwtUtil.validateToken(refreshToken); // 리프레쉬 토큰 검증

            if (email != null) {
                String newAccessToken = jwtUtil.generateAccessToken(email, "USER"); // 새 액세스 토큰 생성
                String newRefreshToken = jwtUtil.generateRefreshToken(email); // 새 리프레쉬 토큰 생성
                return new TokenResponse(newAccessToken, newRefreshToken);
            }
            throw new RuntimeException("Invalid refresh token");
        } catch (Exception e) {
            throw new RuntimeException("Error during token refresh: " + e.getMessage());
        }
    }
}
