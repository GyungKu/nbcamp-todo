package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.CommentRequestDto;
import com.sparta.nbcamptodo.dto.CommentResponseDto;
import com.sparta.nbcamptodo.entity.User;

public interface CommentService {

    CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, User user);

    CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user);

    void deleteComment(Long commentId, User user);

}
