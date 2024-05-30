package com.scw.springtodomanagement.common.exception.global;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalUnauthorizedException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalUnauthorizedException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}
