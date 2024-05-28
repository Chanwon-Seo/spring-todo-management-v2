package com.scw.springtodomanagement.common.exceptionhadler;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.common.exception.global.GlobalDuplicateException;
import com.scw.springtodomanagement.common.exception.post.PostUnauthorizedException;
import com.scw.springtodomanagement.common.exception.user.MemberDuplicateException;
import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Order(1)
@RestControllerAdvice
public class ApiExceptionHandler {
    /**
     * Api 요청에 동작 중 예외가 발생한 경우
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ErrorResponse> apiException(ApiException e) {
        log.error("", e);

        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(MemberDuplicateException.class)
    protected ResponseEntity<ErrorResponse> memberDuplicateException(MemberDuplicateException e) {

        String name = e.getClass().getSimpleName();
        log.error("MemberDuplicateException 해당 클래스입니다. {}", name);

        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }

    @ExceptionHandler(PostUnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> postUnauthorizedException(PostUnauthorizedException e) {

        String name = e.getClass().getSimpleName();
        log.error("PostUnauthorizedException 해당 클래스입니다. {}", name);

        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity.status(errorCode.getHttpStatusCode())
                .body(ErrorResponse.of(errorCode));
    }


}
