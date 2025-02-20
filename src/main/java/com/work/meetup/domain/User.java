package com.work.meetup.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false) // username을 NULL 허용하지 않음
    private String username;

    @Column(nullable = false)
    private String provider; // 자체 회원가입(LOCAL) or OAuth2 (GOOGLE, NAVER, KAKAO)

    @Column
    private String profile; // 프로필 이미지 URL (nullable)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 계정 생성 날짜

    @Column(nullable = false)
    private LocalDateTime updatedAt; // 마지막 수정 날짜

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    //  새로운 생성자 (생성 시간 자동 설정)
    public User(String email, String password, String username, String provider, String profile) {
        this.email = email;
        this.password = password != null ? password : "";
        this.username = username;
        this.provider = provider;
        this.profile = profile;
        this.createdAt = LocalDateTime.now(); //  생성 시 자동 설정
        this.updatedAt = LocalDateTime.now(); //  생성 시 자동 설정
    }

    //  업데이트 시간 갱신 메서드
    public void updateProfile(String profile) {
        this.profile = profile;
        this.updatedAt = LocalDateTime.now();
    }
}
