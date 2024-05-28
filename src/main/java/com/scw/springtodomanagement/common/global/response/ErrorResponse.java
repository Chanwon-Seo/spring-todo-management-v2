package com.scw.springtodomanagement.common.global.response;

import com.scw.springtodomanagement.common.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class ErrorResponse<T> extends RestApiResponse {

//    @JsonInclude(JsonInclude.Include.NON_EMPTY)
//    private final ConcurrentHashMap<String, String> validation;

    protected ErrorResponse(boolean success, int code, String message) {
        super(success, code, message, null);
    }

    protected ErrorResponse(boolean success, int code, String message, T data) {
        super(success, code, message, data);
    }


    public static <T> ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse<>(false,
                errorCode.getHttpStatusCode(),
                errorCode.getDescription());
    }

    public static <T> ErrorResponse of(ErrorCode errorCode, T data) {
        return new ErrorResponse<>(false,
                errorCode.getHttpStatusCode(),
                errorCode.getDescription(),
                data);
    }
}
