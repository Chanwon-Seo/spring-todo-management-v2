package com.scw.springtodomanagement.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberLoginHistoryStatus {
    SUCCESS("success"),
    FAILURE("failure")
    ;

    private final String statueTypeValue;
}
