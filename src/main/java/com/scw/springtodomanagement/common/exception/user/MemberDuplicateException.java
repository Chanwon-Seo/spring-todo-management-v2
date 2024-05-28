package com.scw.springtodomanagement.common.exception.user;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.exception.errorcode.MemberErrorCode;
import com.scw.springtodomanagement.common.exception.global.GlobalDuplicateException;

public class MemberDuplicateException extends GlobalDuplicateException {

    private static final ErrorCode ERROR_CODE = MemberErrorCode.ALREADY_EXISTS_USER;

    public MemberDuplicateException() {
        super(ERROR_CODE);
    }

}