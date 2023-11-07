package com.programmers.happylotto.controller;


import com.programmers.happylotto.dto.OrderRequestDto;
import com.programmers.happylotto.dto.OrderResponseDto;
import com.programmers.happylotto.entity.Order;
import com.programmers.happylotto.exception.LottoErrorCode;
import com.programmers.happylotto.exception.UserErrorCode;
import com.programmers.happylotto.service.OrderService;
import com.programmers.happylotto.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<Object> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        String email = orderRequestDto.email();
        List<List<Integer>> lottoList = orderRequestDto.lottoList();

        if (!Validator.checkEmail(email)) {
            throw new IllegalArgumentException(UserErrorCode.INVALID_EMAIL_PATTERN.getDescription());
        }
        if (!Validator.checkLottoList(lottoList)) {
            throw new IllegalArgumentException(LottoErrorCode.OUT_BOUND_NUMBERS.getDescription());
        }
        try {
            Order order = orderService.createOrder(orderRequestDto);
            return ResponseEntity.ok(new OrderResponseDto(order));
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(UserErrorCode.NO_SUCH_USER.getDescription());
        }
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<Object> getOrderInfo(@PathVariable UUID orderId) {
        try {
            OrderResponseDto orderResponseDto = orderService.getOrder(orderId);
            return ResponseEntity.ok(orderResponseDto);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(UserErrorCode.NO_SUCH_USER.getDescription());
        }
    }
}
