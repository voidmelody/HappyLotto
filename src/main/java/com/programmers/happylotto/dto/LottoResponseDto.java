package com.programmers.happylotto.dto;

import com.programmers.happylotto.entity.Lotto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LottoResponseDto {

    private String number;
    private Integer prize;

    public LottoResponseDto(Lotto lotto) {
        this.number = lotto.getNumber();
        this.prize = lotto.getPrize();
    }
}
