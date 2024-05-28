package com.scw.springtodomanagement.common.exception.errorcode;

public interface ErrorCode {
    Integer getHttpStatusCode();

    String getDescription();
}
