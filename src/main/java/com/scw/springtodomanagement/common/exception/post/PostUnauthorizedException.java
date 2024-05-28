package com.scw.springtodomanagement.common.exception.post;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.exception.errorcode.PostErrorCode;
import com.scw.springtodomanagement.common.exception.global.GlobalUnauthorizedException;

public class PostUnauthorizedException extends GlobalUnauthorizedException {
    private static final ErrorCode ERROR_CODE = PostErrorCode.NOT_UNAUTHORIZED_POST;

    public PostUnauthorizedException() {
        super(ERROR_CODE);
    }
}
