package com.scw.springtodomanagement.common.exception.errorcode;

import com.scw.springtodomanagement.common.statuscode.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PostErrorCode implements ErrorCode {
    UNSUPPORTED_EMAIL_DOMAIN(StatusCode.BAD_REQUEST.code, "지원되지 않는 이메일입니다."),
    PASSWORD_VERIFY_FAIL(StatusCode.BAD_REQUEST.code, "일치하지 않는 비밀번호 입니다."),
    NOT_FOUND_POST(StatusCode.BAD_REQUEST.code, "존재하지 않는 게시물입니다."),
    POST_ALREADY_DELETED(StatusCode.BAD_REQUEST.code, "이미 삭제된 게시물입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}