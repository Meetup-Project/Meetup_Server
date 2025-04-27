package com.work.meetup.global.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {
    @CreatedDate
    private LocalDate createDate; //Entity 생성 및 저장 시 생성일시 자동 저장

    @LastModifiedDate
    private LocalDate updateDate; //조회한 Entity의 값 변경 시 수정일시 자동 저장
}
