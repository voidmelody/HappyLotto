package com.programmers.happylotto.repository;

import com.programmers.happylotto.entity.User;
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

    @DisplayName("유저 이름과 이메일을 통해 유저를 조회할 수 있다.")
    @Test
    void findByUsernameAndEmail() {
        //given
        String targetEmail = "test1234@gmail.com";
        String targetUsername = "test봇";
        User user = User.builder()
                .userId(UUID.randomUUID())
                .username(targetUsername)
                .email(targetEmail)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(user);

        //when
        userRepository.save(user);

        //then
        Optional<User> foundUserOptional = userRepository.findByUsernameAndEmail(targetUsername, targetEmail);

        assertThat(foundUserOptional)
                .isPresent()
                .contains(user);
    }
}