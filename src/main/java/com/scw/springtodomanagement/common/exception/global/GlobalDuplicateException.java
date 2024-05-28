package com.scw.springtodomanagement.common.exception.global;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class GlobalDuplicateException extends RuntimeException {

    private final ErrorCode errorCode;

    public GlobalDuplicateException(ErrorCode errorCode) {
        super(errorCode.getDescription());
        this.errorCode = errorCode;
    }
}