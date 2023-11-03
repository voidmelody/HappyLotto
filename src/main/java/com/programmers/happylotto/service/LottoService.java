package com.programmers.happylotto.service;

import com.programmers.happylotto.domain.Prize;
import com.programmers.happylotto.entity.Lotto;
import com.programmers.happylotto.entity.Order;
import com.programmers.happylotto.entity.User;
import com.programmers.happylotto.repository.LottoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Transactional
@Service
public class LottoService {
    private final LottoRepository lottoRepository;
    private final Random random = new Random();

    private List<Integer> createWinningLottoNumbers() {
        List<Integer> lottoWinningNumbers = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            lottoWinningNumbers.add(random.nextInt(45) + 1); // 1부터 45까지의 랜덤 정수 생성
        }
        return lottoWinningNumbers;
    }

    public List<Lotto> saveAll(List<Lotto> lottoList) {
        return lottoRepository.saveAll(lottoList);
    }

    public List<Lotto> getLottoList(List<List<Integer>> lottoNumbers, User user, Order order) {
        List<Integer> winningLottoNumbers = createWinningLottoNumbers();
        List<Lotto> lottoList = new ArrayList<>();
        for (List<Integer> numbers : lottoNumbers) {
            int matchCount = numbers.stream()
                    .filter(winningLottoNumbers::contains)
                    .toList().size();
            int money = Prize.getMoney(matchCount);
            Lotto lotto = Lotto.builder()
                    .user(user)
                    .order(order)
                    .number(numbers.toString())
                    .prize(money)
                    .build();
            lottoList.add(lotto);
        }
        return lottoList;
    }

}
