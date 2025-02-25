package com.work.meetup.service;

import com.work.meetup.config.JwtUtil;
import com.work.meetup.domain.User;
import com.work.meetup.repository.UserRepository;
import com.work.meetup.dto.OAuth2UserInfo;
import com.work.meetup.security.CustomOAuth2User;
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
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(provider, attributes);

        Optional<User> existingUser = userRepository.findByEmail(userInfo.getEmail());
        User user = existingUser.orElseGet(() -> registerOAuthUser(provider, userInfo));

        return new CustomOAuth2User(user, attributes, jwtUtil.generateAccessToken(user.getEmail(), user.getRole(), user.getId()));
    }

    private User registerOAuthUser(String provider, OAuth2UserInfo userInfo) {
        return userRepository.save(new User(
                userInfo.getEmail(),
                null,
                userInfo.getName(),
                userInfo.getProfileImage(),
                provider.toUpperCase(),
                "USER"
        ));
    }
}
