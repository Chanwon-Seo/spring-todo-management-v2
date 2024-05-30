package com.scw.springtodomanagement.common.exception.user;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.exception.errorcode.MemberErrorCode;
import com.scw.springtodomanagement.common.exception.global.GlobalLoginException;

public class LoginUserNotFoundException extends GlobalLoginException {
    private static final ErrorCode ERROR_CODE = MemberErrorCode.USER_NOT_FOUND;

    public LoginUserNotFoundException() {
        super(ERROR_CODE);
    }
}
