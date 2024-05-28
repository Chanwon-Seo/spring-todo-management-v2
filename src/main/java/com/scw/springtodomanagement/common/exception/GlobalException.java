package com.scw.springtodomanagement.common.exception;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;

public interface GlobalException {
    ErrorCode getErrorCode();
}
