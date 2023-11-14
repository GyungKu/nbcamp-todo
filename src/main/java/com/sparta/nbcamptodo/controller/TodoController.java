package com.sparta.nbcamptodo.controller;

import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoResponseDto;
import com.sparta.nbcamptodo.dto.UserRequestDto;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<GlobalResponseDto> createTodo(@RequestBody TodoRequestDto requestDto) {
        UserRequestDto userDto = new UserRequestDto("userA", "12345678");
        User user = new User(userDto);
        GlobalResponseDto<TodoResponseDto> response = new GlobalResponseDto<>("생성 성공", todoService.createTodo(requestDto, user));
        return ResponseEntity.status(CREATED).body(response);
    }

}
