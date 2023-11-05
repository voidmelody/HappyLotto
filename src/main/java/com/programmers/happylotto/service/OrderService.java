package com.programmers.happylotto.service;

import com.programmers.happylotto.dto.OrderRequestDto;
import com.programmers.happylotto.entity.Lotto;
import com.programmers.happylotto.entity.Order;
import com.programmers.happylotto.entity.User;
import com.programmers.happylotto.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final LottoService lottoService;

    public Order createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        User user = userService.getUser(orderRequestDto.username(), orderRequestDto.email());
        Order order = constructOrder(user);
        Order entityOrder = save(order);

        List<List<Integer>> lottoNumbers = orderRequestDto.lottoList();
        List<Lotto> lottoList = lottoService.getLottoList(lottoNumbers, user, entityOrder);
        lottoService.saveAll(lottoList);

        User updateUser = changeUserRevenue(lottoList, user);
        userService.save(updateUser);

        entityOrder.setLottos(lottoList);
        return entityOrder;
    }

    private Order constructOrder(User user) {
        return Order.builder()
                .orderId(UUID.randomUUID())
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private User changeUserRevenue(List<Lotto> lottoList, User user) {
        int revenue = lottoList.stream().mapToInt(Lotto::getPrize).sum();
        user.setRevenue(user.getRevenue() + revenue);
        return user;
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
