package com.programmers.happylotto.controller;

import com.programmers.happylotto.dto.OrderRequestDto;
import com.programmers.happylotto.entity.Order;
import com.programmers.happylotto.entity.User;
import com.programmers.happylotto.repository.UserRepository;
import com.programmers.happylotto.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

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

    static final String username = "testName";
    static final String email = "test@mail.com";
    static final List<Integer> lotto1 = List.of(1, 2, 3, 4, 5, 6);
    static final List<Integer> lotto2 = List.of(3, 4, 5, 6, 7, 8);
    static final List<List<Integer>> lottoList = List.of(lotto1, lotto2);


    @DisplayName("기존 유저가 있는 경우 주문을 생성할 수 있다.")
    @Test
    void createOrderTest() throws Exception {
        //given
        OrderRequestDto orderRequestDto = new OrderRequestDto(username, email, lottoList);
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("username", orderRequestDto.username());
        requestMap.put("email", orderRequestDto.email());
        requestMap.put("lottoList", orderRequestDto.lottoList());
        String content = new ObjectMapper().writeValueAsString(requestMap);

        System.out.println(content);

        //when
        MvcResult mvcResult = mockMvc.perform(post("/orders").contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andReturn();
        var responseMap = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Map.class);
        //then
        assertThat(responseMap).containsEntry("username", username);
        List<Object> lottos = List.of(responseMap.get("lottoList"));
        assertThat(lottos.toString()).containsOnlyOnce(lotto1.toString()).containsOnlyOnce(lotto2.toString());
    }

    @DisplayName("존재하는 order를 orderId를 통해 조회할 수 있다.")
    @Test
    void getOrderInfoTest() throws Exception {
        //given
        OrderRequestDto orderRequestDto = new OrderRequestDto(username, email, lottoList);
        Order order = orderService.createOrder(orderRequestDto);
        UUID orderId = order.getOrderId();

        //when
        MvcResult mvcResult = mockMvc.perform(get("/orders/" + orderId))
                .andDo(print())
                .andReturn();
        var responseMap = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Map.class);

        //then
        assertThat(responseMap.get("orderId")).isEqualTo(orderId.toString());
    }
}
