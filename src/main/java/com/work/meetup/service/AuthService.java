package com.work.meetup.service;

import com.work.meetup.config.JwtUtil;
import com.work.meetup.domain.User;
import com.work.meetup.dto.LoginRequest;
import com.work.meetup.dto.SignupRequest;
import com.work.meetup.dto.TokenResponse;
import com.work.meetup.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public String registerUser(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "이미 가입된 이메일입니다.";
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .username(request.getUsername())
                .profile(request.getProfile())
                .provider("LOCAL")
                .createdAt(LocalDateTime.now()) //  추가 안전 장치
                .updatedAt(LocalDateTime.now()) //  추가 안전 장치
                .build();

        userRepository.save(user);
        return "회원가입 성공";
    }



    public TokenResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                String accessToken = jwtUtil.generateAccessToken(user.getEmail(), "USER");
                String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());
                return new TokenResponse(accessToken, refreshToken);
            }
        }
        throw new RuntimeException("이메일 또는 비밀번호가 일치하지 않습니다.");
    }
}
