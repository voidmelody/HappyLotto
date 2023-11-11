package com.programmers.happylotto.service;

import com.programmers.happylotto.dto.OrderRequestDto;
import com.programmers.happylotto.entity.Lotto;
import com.programmers.happylotto.entity.Order;
import com.programmers.happylotto.entity.User;
import com.programmers.happylotto.exception.UserErrorCode;
import com.programmers.happylotto.repository.OrderRepository;
import com.programmers.happylotto.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class OrderServiceTest {

    static final String username = "testName";
    static final String email = "test@mail.com";
    static final List<Integer> lotto1 = List.of(1, 2, 3, 4, 5, 6);
    static final List<Integer> lotto2 = List.of(3, 4, 5, 6, 7, 8);
    static final List<List<Integer>> lottoList = List.of(lotto1, lotto2);

    @Autowired
    OrderService orderService;

    @BeforeEach
    void setUp(@Autowired UserRepository userRepository) {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .username(username)
                .email(email)
                .build();
        userRepository.save(user);
    }

    @AfterEach
    void tearDown(@Autowired OrderRepository orderRepository, @Autowired UserRepository userRepository) {
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }


    @DisplayName("이름과 이메일, 로또 리스트를 통해 주문을 생성할 수 있다.")
    @Test
    void createOrderTest() {
        //given
        OrderRequestDto orderRequestDto = new OrderRequestDto(username, email, lottoList);

        //when
        Order order = orderService.createOrder(orderRequestDto);

        //then
        assertThat(order.getUser().getUsername()).isEqualTo(username);
        assertThat(order.getUser().getEmail()).isEqualTo(email);
        assertThat(order.getLottos().stream().map(Lotto::getNumber)).contains(lotto1.toString())
                .contains(lotto2.toString());
    }

    @DisplayName("존재하지 않는 유저로는 주문을 생성할 수 없다.")
    @Test
    void createOrderNotRegisterUser() {
        //given
        String notRegisterUsername = "wrongName";
        OrderRequestDto orderRequestDto = new OrderRequestDto(notRegisterUsername, email, lottoList);

        //when //then
        assertThatThrownBy(() -> orderService.createOrder(orderRequestDto))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessage(UserErrorCode.NO_SUCH_USER.getDescription());
    }
}