package com.hoshuttle.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 공통 에러
    INTERNAL_SERVER_ERROR(500, "서버 내부 오류"),
    INVALID_REQUEST(400, "잘못된 요청"),

    // 예시: route 도메인
    ROUTE_NOT_FOUND(404, "노선 정보를 찾을 수 없습니다"),

    // 예시: user 도메인
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다");

    private final int status;
    private final String message;
}
