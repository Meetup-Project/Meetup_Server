package com.work.meetup.domain.auth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
    private String email;
    private String password;
//    private String profile; // 프로필 사진 URL (선택)
    private String name;

    public SignupRequest(String email, String password, String username) {
        this.email = email;
        this.password = password;
//        this.profile = profile;
        this.name =username;
    }
}
