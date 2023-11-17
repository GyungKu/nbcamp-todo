package com.sparta.nbcamptodo.controller;

import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.security.UserDetailsImpl;
import com.sparta.nbcamptodo.service.TodoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<GlobalResponseDto> createTodo(@RequestBody @Valid TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        GlobalResponseDto<TodoDetailResponseDto> response = new GlobalResponseDto<>("할 일 생성 성공", todoService.createTodo(requestDto, userDetails.getUser()));
        return ResponseEntity.status(CREATED).body(response);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<GlobalResponseDto> getTodo(@PathVariable Long todoId) {
        TodoDetailResponseDto responseDto = todoService.getTodo(todoId);
        GlobalResponseDto<TodoDetailResponseDto> response = new GlobalResponseDto<>("할 일 상세 조회", responseDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/todoList")
    public ResponseEntity<GlobalResponseDto> getTodoList() {
        List<TodoListResponseDto> responseDto = todoService.getTodoList();
        GlobalResponseDto<List<TodoListResponseDto>> todoList = new GlobalResponseDto<>("할 일 유저벌 목록 조회", responseDto);
        return ResponseEntity.ok(todoList);
    }

    @PatchMapping("/todo/{todoId}")
    public ResponseEntity<GlobalResponseDto> updateTodo(@PathVariable Long todoId, @RequestBody TodoRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        TodoDetailResponseDto responseDto = todoService.updateTodo(todoId, requestDto, userDetails.getUser());
        GlobalResponseDto<TodoDetailResponseDto> response = new GlobalResponseDto<>("할 일 수정 성공", responseDto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/todo/{todoId}")
    public ResponseEntity<GlobalResponseDto> completedTodo(@PathVariable Long todoId, Boolean complete, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        todoService.completedTodo(todoId, userDetails.getUser(), complete);
        return ResponseEntity.ok(new GlobalResponseDto("할 일 완료 처리 성공", "succeed todo complete update"));
    }

}
