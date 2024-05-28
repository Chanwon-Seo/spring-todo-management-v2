package com.scw.springtodomanagement.domain.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommendStatusType {

    ENABLE("enable"),
    DISABLE("disable"),
    ;

    private final String statueTypeValue;

}
