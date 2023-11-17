package com.sparta.nbcamptodo.controller;

import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<GlobalResponseDto> signup(@Valid @RequestBody SignRequestDto requestDto) {
        String message = userService.signup(requestDto);
        return ResponseEntity.status(CREATED).body(new GlobalResponseDto(message, "succeed signup"));
    }

}
