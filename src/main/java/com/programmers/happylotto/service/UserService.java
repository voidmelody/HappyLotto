package com.programmers.happylotto.service;

import com.programmers.happylotto.dto.UserResponseDto;
import com.programmers.happylotto.entity.User;
import com.programmers.happylotto.exception.UserErrorCode;
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

    public UserResponseDto register(String username, String email) {
        Optional<User> existingUser = userRepository.findByUsernameAndEmail(username, email);
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException(UserErrorCode.ALREADY_EXIST_USER.getDescription());
        }
        User newUser = User.builder()
                .userId(UUID.randomUUID())
                .username(username)
                .email(email)
                .createdAt(LocalDateTime.now())
                .build();
        userRepository.save(newUser);
        return new UserResponseDto(newUser);
    }

    public User getUser(String username, String email) {
        return userRepository.findByUsernameAndEmail(username, email).orElseThrow();
    }

    public UserResponseDto getUserInfo(String username, String email) {
        User user = userRepository.findByUsernameAndEmail(username, email).orElseThrow();
        return new UserResponseDto(user);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
