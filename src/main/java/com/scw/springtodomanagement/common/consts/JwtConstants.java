package com.scw.springtodomanagement.common.consts;

public class JwtConstants {
    // Header KEY 값
    public static final String ACCESS_TOKEN_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "Authorization-refresh";

    // Token 식별자
    public static final String BEARER_PREFIX = "Bearer ";
    // 토큰 만료시간
    public static final long ACCESS_TOKEN_TIME = 60 * 60 * 1000L; // 60분
    public static final long REFRESH_TOKEN_TIME = 24 * 60 * 60 * 1000L; // 3일
//    private final long ACCESS_TOKEN_TIME = 30 * 1000L; // 30초
//    private final long REFRESH_TOKEN_TIME = 60 * 1000L; // 1분


}
