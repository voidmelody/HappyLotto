package com.programmers.happylotto.dto;

import com.programmers.happylotto.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private UUID userId;
    private String username;
    private String email;
    private Integer revenue;
    private List<OrderResponseDto> orders = new ArrayList<>();

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.revenue = user.getRevenue();
        this.orders = user.getOrders().stream()
                .map(OrderResponseDto::new)
                .sorted(Comparator.comparing(OrderResponseDto::getCreatedAt))
                .toList();
    }
}
