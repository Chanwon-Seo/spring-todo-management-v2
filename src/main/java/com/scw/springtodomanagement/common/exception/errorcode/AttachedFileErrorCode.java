package com.scw.springtodomanagement.common.exception.errorcode;

import com.scw.springtodomanagement.common.statuscode.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AttachedFileErrorCode implements ErrorCode {
    NOT_FOUND_COMMEND(StatusCode.UNAUTHORIZED.code, "해당 댓글을 찾을 수 없습니다."),
    IMAGE_PROCESSING_ERROR(StatusCode.BAD_REQUEST.code, "이미지 처리 중 오류가 발생했습니다."),
    INVALID_FILENAME(StatusCode.BAD_REQUEST.code, "유효하지 않은 이미지 파일명입니다."),
    ATTACHED_FILE_ALREADY_DELETED(StatusCode.BAD_REQUEST.code, "이미 삭제된 댓글입니다."),
    ;

    private final Integer httpStatusCode;
    private final String description;
}
