package com.programmers.happylotto.utils;

import java.util.List;
import java.util.regex.Pattern;

public class Validator {

    private Validator() {
        throw new IllegalStateException("Validator");
    }

    public static boolean checkEmail(String email) {
        return Pattern.matches("\\b[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,4}\\b", email);
    }

    public static boolean checkLottoList(List<List<Integer>> lottoList){
        return lottoList.stream().allMatch(lottoNumbers -> lottoNumbers.size() == 6);
    }

}
