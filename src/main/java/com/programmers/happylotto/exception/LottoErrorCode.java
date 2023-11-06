package com.programmers.happylotto.exception;

import lombok.Getter;

@Getter
public enum LottoErrorCode {
    OUT_BOUND_NUMBERS("로또 번호는 6개여야 합니다.");

    LottoErrorCode(String description) {
        this.description = description;
    }

    private final String description;

}
