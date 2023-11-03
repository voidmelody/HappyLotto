package com.programmers.happylotto.dto;

import com.programmers.happylotto.entity.Lotto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponseDto(UUID orderId, UUID userId, LocalDateTime createdAt, List<Lotto> lottoList) {
}
