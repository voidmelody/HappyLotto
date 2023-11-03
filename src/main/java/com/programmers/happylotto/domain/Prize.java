package com.programmers.happylotto.domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Prize {
    FIRST(6, 2000000000),
    SECOND(5, 50000000),
    THIRD(4, 1500000),
    FOURTH(3, 50000),
    FIFTH(2, 5000),
    SIXTH(1, 0),
    SEVENTH(0, 0);


    private final int matchCount;
    private final int money;

    Prize(int matchCount, int money) {
        this.matchCount = matchCount;
        this.money = money;
    }

    public static int getMoney(int matchCount) {
        return Arrays.stream(Prize.values())
                .filter(p -> p.matchCount == matchCount)
                .map(p -> p.money)
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
