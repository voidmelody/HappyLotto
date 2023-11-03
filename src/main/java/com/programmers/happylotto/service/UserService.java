package com.programmers.happylotto.service;

import com.programmers.happylotto.entity.User;
import com.programmers.happylotto.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getUserByEmailOrCreateNew(String username, String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.orElseGet(() -> {
            User newUser = User.builder()
                    .userId(UUID.randomUUID())
                    .username(username)
                    .email(email)
                    .createdAt(LocalDateTime.now())
                    .build();
            userRepository.save(newUser);
            return newUser;
        });
    }

    public User getUserInfo(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }
}
