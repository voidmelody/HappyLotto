package com.programmers.happylotto.repository;

import com.programmers.happylotto.entity.Order;
import com.programmers.happylotto.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    void setUser(@Autowired UserRepository userRepository) {
        userRepository.save(user);
    }

    static final User user = User.builder()
            .userId(UUID.randomUUID())
            .username("name1")
            .email("mail1@email.com")
            .createdAt(LocalDateTime.now())
            .build();

    @DisplayName("orderId를 통해 오더를 조회할 수 있다.")
    @Test
    void findByOrderId() {
        //given
        UUID orderId = UUID.randomUUID();

        Order order = Order.builder()
                .orderId(orderId)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        //when //then
        Optional<Order> optionalOrder = orderRepository.findByOrderId(orderId);

        Assertions.assertThat(optionalOrder)
                .isPresent()
                .contains(order);
    }
}
