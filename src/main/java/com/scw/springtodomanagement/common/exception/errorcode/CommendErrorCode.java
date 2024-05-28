package com.scw.springtodomanagement.common.exception.errorcode;

import com.scw.springtodomanagement.common.statuscode.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CommendErrorCode implements ErrorCode {
    NOT_FOUND_COMMEND(StatusCode.UNAUTHORIZED.code, "해당 댓글을 찾을 수 없습니다."),
    NOT_UNAUTHORIZED_COMMEND(StatusCode.UNAUTHORIZED.code, "권한이 없는 댓글입니다."),
    COMMEND_ALREADY_DELETED(StatusCode.BAD_REQUEST.code, "이미 삭제된 댓글입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
