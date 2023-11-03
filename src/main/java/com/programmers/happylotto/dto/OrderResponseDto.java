package com.programmers.happylotto.dto;

import com.programmers.happylotto.entity.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class OrderResponseDto {
    private UUID orderId;
    private String username;
    private LocalDateTime createdAt;
    private List<LottoResponseDto> lottoList = new ArrayList<>();

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.username = order.getUser().getUsername();
        this.createdAt = order.getCreatedAt();
        this.lottoList = order.getLottos().stream()
                .map(LottoResponseDto::new)
                .toList();
    }
}
