package com.scw.springtodomanagement.common.exception.errorcode;

import com.scw.springtodomanagement.common.statuscode.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    INVALID_ARGUMENT_ERROR(StatusCode.BAD_REQUEST.code, "올바르지 않은 파라미터입니다."),
    BAD_REQUEST(StatusCode.BAD_REQUEST.code, "올바르지 않은 요청 정보입니다."),
    NOT_FOUND(StatusCode.BAD_REQUEST.code, "올바르지 않은 요청 URL입니다."),
    SERVER_ERROR(StatusCode.INTERNAL_SERVER_ERROR.code, "서버 에러입니다."),
    METHOD_NOT_ALLOWED(StatusCode.METHOD_NOT_ALLOWED.code, "올바르지 않는 요청 메소드입니다.");

    private final Integer httpStatusCode;
    private final String description;
}
