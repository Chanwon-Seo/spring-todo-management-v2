package com.scw.springtodomanagement.common.exception.errorcode;

import com.scw.springtodomanagement.common.statuscode.StatusCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageErrorCode implements ErrorCode {

    UNSUPPORTED_IMAGE_EXTENSION(StatusCode.BAD_REQUEST.getCode(), "지원하지 않는 확장자입니다."),
    NOT_FOUND_IMAGE_FILE(StatusCode.BAD_REQUEST.getCode(), "존재하지 않는 이미지입니다.");;


    private final Integer httpStatusCode;
    private final String description;
}
