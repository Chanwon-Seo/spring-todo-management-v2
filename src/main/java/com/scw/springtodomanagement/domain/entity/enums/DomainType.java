package com.scw.springtodomanagement.domain.entity.enums;

import com.scw.springtodomanagement.common.errorcode.PostErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Slf4j
public enum DomainType {
    GOOGLE("google.com"),
    NAVER("naver.com"),
    GITHUB("github.com"),

    ;
    private final String domainName;

    DomainType(String domainName) {
        this.domainName = domainName;
    }

    public static void fromDomainValidation(String inputDomainData) {
        String[] domainData = inputDomainData.split("@");
        log.info("test =  {} | {} | {}", domainData, domainData[0], domainData[1]);
        for (DomainType value : values()) {
            if (domainData[1].equals(value.getDomainName())) {
                return;
            }
        }
        throw new ApiException(PostErrorCode.UNSUPPORTED_EMAIL_DOMAIN);
    }

}
