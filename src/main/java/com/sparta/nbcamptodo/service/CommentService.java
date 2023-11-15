package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.CommentRequestDto;
import com.sparta.nbcamptodo.dto.CommentResponseDto;
import com.sparta.nbcamptodo.entity.Comment;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.repository.CommentRepository;
import com.sparta.nbcamptodo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    @Transactional
    public CommentResponseDto createComment(Long todoId, CommentRequestDto requestDto, User user) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할 일 입니다."));
        Comment comment = new Comment(requestDto.getContent(), user, todo);
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }
}
