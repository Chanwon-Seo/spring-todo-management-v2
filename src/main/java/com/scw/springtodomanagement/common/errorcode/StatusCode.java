package com.scw.springtodomanagement.common.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;

@Getter
@RequiredArgsConstructor
public enum StatusCode {

    OK(200),
    CREATED(201),

    BAD_REQUEST(400),
    NOT_FOUND(404),
    METHOD_NOT_ALLOWED(405),

    INTERNAL_SERVER_ERROR(500),
    ;

    public final int code;

}
