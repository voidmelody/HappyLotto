package com.programmers.happylotto.dto;

import java.util.List;

public record OrderRequestDto(String username,
                              String email,
                              List<List<Integer>> lottoList) {
}
