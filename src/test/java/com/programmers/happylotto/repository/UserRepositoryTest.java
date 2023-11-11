package com.programmers.happylotto.repository;

import com.programmers.happylotto.entity.User;
import org.junit.jupiter.api.AfterEach;
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

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUser() {
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.delete(user);
    }

    static final String targetEmail = "test1234@gmail.com";
    static final String targetUsername = "test봇";
    static final User user = User.builder()
            .userId(UUID.randomUUID())
            .username(targetUsername)
            .email(targetEmail)
            .createdAt(LocalDateTime.now())
            .build();

    @DisplayName("유저 이름과 이메일을 통해 유저를 조회할 수 있다.")
    @Test
    void findByUsernameAndEmail() {
        //given
        String targetEmail = "test1234@gmail.com";
        String targetUsername = "test봇";

        //when //then
        Optional<User> foundUserOptional = userRepository.findByUsernameAndEmail(targetUsername, targetEmail);

        assertThat(foundUserOptional)
                .isPresent()
                .contains(user);
    }

    @DisplayName("존재하지 않은 이름을 통해서는 유저를 조회할 수 없다.")
    @Test
    void findByInvalidUsernameAndEmail(){
        //given
        String invalidUsername = "테스트봇";

        //when //then
        Optional<User> foundInvalidUserOptional = userRepository.findByUsernameAndEmail(invalidUsername, targetEmail);

        assertThat(foundInvalidUserOptional)
                .isEmpty();
     }

    @DisplayName("존재하지 않은 이메일을 통해서는 유저를 조회할 수 없다.")
    @Test
    void findByUsernameAndInvalidEmail(){
        //given
        String invalidEmail = "test1234@naver.com";

        //when //then
        Optional<User> foundInvalidUserOptional = userRepository.findByUsernameAndEmail(targetUsername, invalidEmail);

        assertThat(foundInvalidUserOptional)
                .isEmpty();
    }
}