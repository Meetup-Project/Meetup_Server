package com.work.meetup.dto;

import com.work.meetup.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserDto {
    private String email;
    private String profile;
    private String provider;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDto(User user) {
        this.email = user.getEmail();
        this.profile = user.getProfile();
        this.provider = user.getProvider();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}