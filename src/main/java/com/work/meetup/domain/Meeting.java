package com.work.meetup.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "meetings")
public class Meeting extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) //모임 제목은 필수
    @Size(min = 1, max = 100) //최소, 최대 값 지정
    private String Title;

    @Column(nullable = false) //모임 소개는 필수
    @Size(min = 1, max = 3000) //최소, 최대 값 지정
    private String Description;

    private String Location;

    private LocalDateTime MeetingTime;

    @OneToOne() //연관관계 매핑
    @JoinColumn(name = "user_id")
    private User createdBy;

    @Column(nullable = false)
    @CreatedDate //생성일 자동 생성
    private LocalDateTime CreatedAt;

    @Column(nullable = false)
    @LastModifiedDate //수정일 자동 생성
    private LocalDateTime UpdatedAt;

    @Builder
    public Meeting(String Title, String Description, String Location, LocalDateTime MeetingTime, User createdBy) {
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.MeetingTime = MeetingTime;
        this.createdBy = createdBy;
    }

    //수정하는 메소드
    public void update(String Title, String Description, String Location, LocalDateTime MeetingTime) {
        this.Title = Title;
        this.Description = Description;
        this.Location = Location;
        this.MeetingTime = MeetingTime;
    }

}
