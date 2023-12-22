package com.sparta.nbcamptodo.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import com.sparta.nbcamptodo.dto.PageDto;
import com.sparta.nbcamptodo.dto.SortDto;
import com.sparta.nbcamptodo.dto.TodoCondition;
import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoSearchResponseDto;
import com.sparta.nbcamptodo.security.UserDetailsImpl;
import com.sparta.nbcamptodo.service.TodoService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping("/todo")
    public ResponseEntity<GlobalResponseDto> createTodo(
        @RequestBody @Valid TodoRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        GlobalResponseDto<TodoDetailResponseDto> response = new GlobalResponseDto<>
            ("할 일 생성 성공", todoService.createTodo(requestDto, userDetails.getUser()));
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

    @GetMapping("/todoList/search")
    public ResponseEntity<GlobalResponseDto> getTodoListSearch(PageDto pageDto, SortDto sortDto,
        TodoCondition condition) {

        Page<TodoSearchResponseDto> responseDto = todoService.getTodoListSearch(pageDto, sortDto,
            condition);
        return ResponseEntity.ok(new GlobalResponseDto("검색 할 일 조회", responseDto));
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

    @PostMapping("/todo/imageUpload/{todoId}")
    public ResponseEntity<GlobalResponseDto> uploadImage(@PathVariable Long todoId,
        MultipartFile multipartFile, @AuthenticationPrincipal UserDetailsImpl userDetails)
        throws IOException {

        String imageUrl = todoService.uploadImage(todoId, multipartFile, userDetails.getUser());
        return ResponseEntity.ok(new GlobalResponseDto("이미지 주소", imageUrl));
    }

}
