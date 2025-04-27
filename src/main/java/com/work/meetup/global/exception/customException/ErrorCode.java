package com.work.meetup.global.exception.customException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    USER_NOT_FOUND(400,"일치하는 유저를 찾을 수 없습니다."),
    MEETING_NOT_FOUND(400,"일치하는 모임을 찾을 수 없습니다."),
    MEMBER_NOT_FOUND(400,"일치하는 멤버를 찾을 수 없습니다."),
    UNAUTHORIZED(401, "권한이 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버에서 오류가 발생하였습니다.");

    private final int httpStatus;
    private final String message;
}