package com.scw.springtodomanagement.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberRoleType {

    USER("USER"),
    ADMIN("ADMIN"),
    ;

    private final String roleName;
}
