package com.scw.springtodomanagement.common.exceptionhadler;

import com.scw.springtodomanagement.common.errorcode.CommonErrorCode;
import com.scw.springtodomanagement.common.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 요청 정보가 잘 못된 경우
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("[handleMethodArgumentTypeMismatchException] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.BAD_REQUEST);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);

        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.METHOD_NOT_ALLOWED);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    protected ResponseEntity handleNoResourceFoundException(NoResourceFoundException e) {
        log.error("[handleNoResourceFoundException] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());

        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.NOT_FOUND);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

    /**
     * 하위에서 잡지 못한 에러
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception e) {
        log.error("[handleException] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());


        ErrorResponse errorResponse = ErrorResponse.of(CommonErrorCode.SERVER_ERROR);

        return ResponseEntity.status(errorResponse.getCode())
                .body(errorResponse);
    }

}
