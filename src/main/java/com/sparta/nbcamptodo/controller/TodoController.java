package com.sparta.nbcamptodo.controller;

import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoResponseDto;
import com.sparta.nbcamptodo.dto.LoginRequestDto;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.security.UserDetailsImpl;
import com.sparta.nbcamptodo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<GlobalResponseDto> createTodo(@RequestBody @Valid TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GlobalResponseDto<TodoResponseDto> response = new GlobalResponseDto<>("생성 성공", todoService.createTodo(requestDto, userDetails.getUser()));
        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<GlobalResponseDto> getTodo(@PathVariable Long todoId) {
        TodoResponseDto responseDto = todoService.getTodo(todoId);
        GlobalResponseDto<TodoResponseDto> response = new GlobalResponseDto<>("success", responseDto);
        return ResponseEntity.status(CREATED).body(response);
    }

}
