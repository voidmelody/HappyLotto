package com.programmers.happylotto.utils;

import java.util.regex.Pattern;

public class Validator {

    private Validator() {
        throw new IllegalStateException("Validator");
    }

    public static boolean checkEmail(String email) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", email);
    }
}
