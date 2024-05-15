package com.scw.springtodomanagement.common.exceptionhadler;

import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.concurrent.ConcurrentHashMap;

import static com.scw.springtodomanagement.common.errorcode.CommonErrorCode.BAD_REQUEST;

@Slf4j
@Order(1)
@RestControllerAdvice
public class ValidExceptionHandler {

    /**
     * 유효성 검사에서 예외가 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException e) {
        log.error("[MethodArgumentNotValidException] cause : {}, message : {} ", NestedExceptionUtils.getMostSpecificCause(e),e.getMessage());

        ConcurrentHashMap<Object, Object> validationMessage = new ConcurrentHashMap<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            validationMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ErrorResponse errorResponse = ErrorResponse.of(BAD_REQUEST, validationMessage);

        return ResponseEntity.status(BAD_REQUEST.getHttpStatusCode())
                .body(errorResponse);
    }


}
