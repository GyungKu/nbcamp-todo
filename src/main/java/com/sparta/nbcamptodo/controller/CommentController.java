package com.sparta.nbcamptodo.controller;

import com.sparta.nbcamptodo.dto.CommentRequestDto;
import com.sparta.nbcamptodo.dto.CommentResponseDto;
import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import com.sparta.nbcamptodo.security.UserDetailsImpl;
import com.sparta.nbcamptodo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/todo/{todoId}/comment")
    public ResponseEntity<GlobalResponseDto> createComment(@PathVariable Long todoId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(todoId, requestDto, userDetails.getUser());
        return ResponseEntity.status(CREATED).body(new GlobalResponseDto("댓글 생성 성공", responseDto));
    }

    @PatchMapping("/todo/comment/{commentId}")
    public ResponseEntity<GlobalResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return ResponseEntity.ok(new GlobalResponseDto("수정 성공", responseDto));
    }
}

