package com.scw.springtodomanagement.common.exceptionhadler;

import com.scw.springtodomanagement.common.errorcode.CommonErrorCode;
import com.scw.springtodomanagement.common.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 요청 정보가 잘 못된 경우
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ErrorCode errorCode = CommonErrorCode.BAD_REQUEST;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, null);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    /**
     * 요청 메서드가 잘 못된 경우
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity handleHttpRequestMethodNotSupportedException(MethodArgumentTypeMismatchException e) {
        log.error("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ErrorCode errorCode = CommonErrorCode.METHOD_NOT_ALLOWED;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, null);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    /**
     * 하위에서 잡지 못한 에러
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e) {
        log.error("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ErrorCode errorCode = CommonErrorCode.SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(errorCode, null);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

}
