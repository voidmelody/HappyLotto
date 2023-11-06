package com.programmers.happylotto.controller;

import com.programmers.happylotto.dto.UserResponseDto;
import com.programmers.happylotto.dto.UserRequestDto;
import com.programmers.happylotto.exception.UserErrorCode;
import com.programmers.happylotto.service.UserService;
import com.programmers.happylotto.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Object> register(@RequestBody UserRequestDto userRequestDto) {
        String username = userRequestDto.username();
        String email = userRequestDto.email();

        if (!Validator.checkEmail(email)) {
            throw new IllegalArgumentException(UserErrorCode.INVALID_EMAIL_PATTERN.getDescription());
        }
        try {
            UserResponseDto userResponseDto = userService.register(username, email);
            return ResponseEntity.ok(userResponseDto);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(UserErrorCode.ALREADY_EXIST_USER.getDescription());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUserInfo(@RequestParam String username,
                                              @RequestParam String email) {
        if (!Validator.checkEmail(email)) {
            throw new IllegalArgumentException(UserErrorCode.INVALID_EMAIL_PATTERN.getDescription());
        }
        try {
            UserResponseDto userResponseDto = userService.getUserInfo(username, email);
            return ResponseEntity.ok(userResponseDto);
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException(UserErrorCode.NO_SUCH_USER.getDescription());
        }
    }
}
