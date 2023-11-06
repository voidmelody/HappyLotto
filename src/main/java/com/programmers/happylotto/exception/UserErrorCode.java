package com.programmers.happylotto.exception;

import lombok.Getter;

@Getter
public enum UserErrorCode {
    INVALID_EMAIL_PATTERN("이메일 형식이 잘못되었습니다."),
    NO_SUCH_USER("해당 내용과 일치하는 유저가 존재하지 않습니다."),
    ALREADY_EXIST_USER("이미 존재하는 유저는 신규 등록할 수 없습니다.");


    UserErrorCode(String description) {
        this.description = description;
    }

    private final String description;


}
