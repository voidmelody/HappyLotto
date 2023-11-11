package com.programmers.happylotto.service;

import com.programmers.happylotto.dto.UserResponseDto;
import com.programmers.happylotto.exception.UserErrorCode;
import com.programmers.happylotto.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    UserService userService;

    @AfterEach
    void tearDown(@Autowired UserRepository userRepository) {
        userRepository.deleteAll();
    }

    @DisplayName("이름과 이메일을 통해 유저를 등록할 수 있다.")
    @Test
    void registerTest(){
        //given
        String username = "testBot";
        String email = "test@email.com";

        //when
        UserResponseDto responseDto = userService.register(username, email);

        //then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getEmail()).isEqualTo(email);
        assertThat(responseDto.getUsername()).isEqualTo(username);
     }

     @DisplayName("이미 존재하는 유저는 등록할 수 없다.")
     @Test
     void registerFailAlreadyExistUserTest(){
         //given
         String username = "testBot";
         String email = "test@email.com";
         userService.register(username, email);

         //when //then
         assertThatThrownBy(() -> userService.register(username, email))
                 .isInstanceOf(IllegalArgumentException.class)
                 .hasMessage(UserErrorCode.ALREADY_EXIST_USER.getDescription());
      }


}