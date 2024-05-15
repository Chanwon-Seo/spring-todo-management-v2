package com.scw.springtodomanagement.common.exceptionhadler;

import com.scw.springtodomanagement.common.errorcode.CommonErrorCode;
import com.scw.springtodomanagement.common.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //모든 에러
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ErrorCode errorCode = CommonErrorCode.SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, null);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

}
