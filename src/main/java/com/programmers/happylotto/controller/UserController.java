package com.programmers.happylotto.controller;

import com.programmers.happylotto.dto.UserRequestDto;
import com.programmers.happylotto.entity.User;
import com.programmers.happylotto.service.UserService;
import com.programmers.happylotto.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Object> registerOrLogin(@RequestBody UserRequestDto userRequestDto) {
        String username = userRequestDto.username();
        String email = userRequestDto.email();

        if (!Validator.checkEmail(email)) {
            String errorMessage = "이메일 형식이 잘못되었습니다.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserByEmailOrCreateNew(username, email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUserInfo(@RequestParam String email){
        if (!Validator.checkEmail(email)) {
            String errorMessage = "이메일 형식이 잘못되었습니다.";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserInfo(email);
        return ResponseEntity.ok(user);
    }
}
