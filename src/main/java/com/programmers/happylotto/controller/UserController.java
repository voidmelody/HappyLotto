package com.programmers.happylotto.controller;

import com.programmers.happylotto.dto.UserResponseDto;
import com.programmers.happylotto.dto.UserRequestDto;
import com.programmers.happylotto.service.UserService;
import com.programmers.happylotto.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
            String errorMessage = "이메일 형식이 잘못되었습니다.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        try{
            UserResponseDto userResponseDto = userService.register(username, email);
            return ResponseEntity.ok(userResponseDto);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getUserInfo(@RequestParam String username,
                                              @RequestParam String email){
        if (!Validator.checkEmail(email)) {
            String errorMessage = "이메일 형식이 잘못되었습니다.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }
        try{
            UserResponseDto userResponseDto = userService.getUserInfo(username, email);
            return ResponseEntity.ok(userResponseDto);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
