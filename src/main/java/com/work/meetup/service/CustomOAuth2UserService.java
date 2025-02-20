package com.work.meetup.service;

import com.work.meetup.config.JwtUtil;
import com.work.meetup.domain.User;
import com.work.meetup.dto.TokenResponse;
import com.work.meetup.repository.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public CustomOAuth2UserService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return oAuth2User;
    }

    public TokenResponse processOAuth2User(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = extractEmail(attributes);
        String profile = extractProfile(attributes);

        if (email == null) {
            throw new RuntimeException("OAuth2 provider did not return an email.");
        }

        Optional<User> existingUser = userRepository.findByEmail(email);
        User user = existingUser.orElseGet(() -> {
            String username = email.split("@")[0];
            User newUser = new User(email, null, username, "OAUTH2", profile);
            return userRepository.save(newUser);
        });

        String accessToken = jwtUtil.generateAccessToken(email, "USER");
        String refreshToken = jwtUtil.generateRefreshToken(email);

        return new TokenResponse(accessToken, refreshToken);
    }

    private String extractEmail(Map<String, Object> attributes) {
        if (attributes.containsKey("email")) {
            return (String) attributes.get("email"); // Google
        } else if (attributes.containsKey("response")) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return (String) response.get("email"); // Naver
        } else if (attributes.containsKey("kakao_account")) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            return (String) kakaoAccount.get("email"); // Kakao
        }
        return null;
    }

    private String extractProfile(Map<String, Object> attributes) {
        if (attributes.containsKey("picture")) {
            return (String) attributes.get("picture"); // Google
        } else if (attributes.containsKey("response")) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");
            return (String) response.get("profile_image"); // Naver
        } else if (attributes.containsKey("properties")) {
            Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
            return (String) properties.get("profile_image"); // Kakao
        }
        return null;
    }
}
