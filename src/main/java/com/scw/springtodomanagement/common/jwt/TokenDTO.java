package com.scw.springtodomanagement.common.jwt;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TokenDTO {

    private String accessToken;
    private String refreshToken;

}
