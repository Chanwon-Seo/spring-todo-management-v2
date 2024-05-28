package com.scw.springtodomanagement.common.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.scw.springtodomanagement.common.statuscode.StatusCode.BAD_REQUEST;
import static com.scw.springtodomanagement.common.statuscode.StatusCode.UNAUTHORIZED;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {
    ALREADY_EXISTS_USER(BAD_REQUEST.code, "이미 가입한 유저입니다."),
    USER_NOT_FOUND(UNAUTHORIZED.code, "유저를 찾을 수 없습니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
