package com.work.meetup.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter //  필요 시 추가 (비밀번호 변경 등을 위해)
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

    @Column(nullable = true) // ✅ username 필수값 유지
    private String username;

    @Column(nullable = true) //  OAuth 유저는 비밀번호가 없을 수 있음
    private String password;

    @Column(nullable = false)
    private String name; //  기존 "username" → "name"으로 변경 (OAuth와 일관성 유지)

    @Column(nullable = false)
    private String provider; // LOCAL, GOOGLE, NAVER, KAKAO

//    @Column
//    private String profile; // 프로필 이미지 URL (nullable)

    @Column(nullable = false)
    private String role; //  "USER", "ADMIN" 등 역할 구분

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    //  새로운 유저 생성 시 사용하는 생성자
    public User(String email, String username, String password, String name, String provider, String role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.role = role;
    }

    //  비밀번호 변경 메서드
    public void setPassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
    }

    //  프로필 업데이트 메서드
    public void updateProfile(String profile) {
//        this.profile = profile;
        this.updatedAt = LocalDateTime.now();
    }
}
