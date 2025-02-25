package com.work.meetup.service;

import com.work.meetup.config.JwtUtil;
import com.work.meetup.domain.User;
import com.work.meetup.dto.LoginRequest;
import com.work.meetup.dto.TokenResponse;
import com.work.meetup.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    //  자체 회원가입
    public User registerUser(String email, String password, String name) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        User newUser = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .name(name)
                .provider("LOCAL") // 자체 회원가입은 "LOCAL"
                .role("USER")
                .build();

        return userRepository.save(newUser);
    }

    //  자체 로그인 (이메일 & 비밀번호 검증)
    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return generateTokenResponse(user);
    }

    //  JWT 생성 (OAuth & 자체 로그인 통합)
    private TokenResponse generateTokenResponse(User user) {
        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
        return new TokenResponse(accessToken, refreshToken);
    }
}
