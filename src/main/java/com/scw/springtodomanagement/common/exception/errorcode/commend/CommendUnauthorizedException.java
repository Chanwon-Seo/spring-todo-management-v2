package com.scw.springtodomanagement.common.exception.errorcode.commend;

import com.scw.springtodomanagement.common.exception.errorcode.CommendErrorCode;
import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.exception.global.GlobalUnauthorizedException;
public class CommendUnauthorizedException extends GlobalUnauthorizedException {
    private static final ErrorCode ERROR_CODE = CommendErrorCode.NOT_UNAUTHORIZED_COMMEND;

    public CommendUnauthorizedException() {
        super(ERROR_CODE);
    }
}
