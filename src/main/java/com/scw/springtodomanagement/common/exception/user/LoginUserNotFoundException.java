package com.scw.springtodomanagement.common.exception.user;

public class LoginUserNotFoundException extends RuntimeException {
    public LoginUserNotFoundException() {
        super("잘못된 유저 정보입니다.");
    }
}
