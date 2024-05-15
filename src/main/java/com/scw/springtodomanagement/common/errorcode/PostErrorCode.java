package com.scw.springtodomanagement.common.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static com.scw.springtodomanagement.common.errorcode.StatusCode.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    UNSUPPORTED_EMAIL_DOMAIN(BAD_REQUEST.code, "지원되지 않는 이메일입니다."),
    PASSWORD_VERIFY_FAIL(BAD_REQUEST.code, "일치하지 않는 비밀번호 입니다."),
    NOT_FOUND_POST(BAD_REQUEST.code, "존재하지 않는 게시물입니다.");

    private final Integer httpStatusCode;
    private final String description;
}