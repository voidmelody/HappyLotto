package com.programmers.happylotto.controller;


import com.programmers.happylotto.dto.OrderRequestDto;
import com.programmers.happylotto.dto.OrderResponseDto;
import com.programmers.happylotto.service.OrderService;
import com.programmers.happylotto.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        String email = orderRequestDto.email();
        List<List<Integer>> lottoList = orderRequestDto.lottoList();

        if (!Validator.checkEmail(email)) {
            String errorMessage = "이메일 형식이 잘못되었습니다.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        if (!Validator.checkLottoList(lottoList)) {
            String errorMessage = "로또 번호는 6개여야 합니다.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        OrderResponseDto orderResponseDto = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderResponseDto);
    }

}
