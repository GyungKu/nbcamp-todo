package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.Comment;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.TodoImage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TodoDetailResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private Boolean complete;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> comments = new ArrayList<>();
    private List<TodoImageDto> images;

    public TodoDetailResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.username = todo.getUser().getUsername();
        this.complete = todo.getComplete();
        this.createdAt = todo.getCreatedAt();
        for (Comment comment : todo.getComments()) {
            comments.add(new CommentResponseDto(comment));
        }
        images = todo.getImages().stream().map(TodoImageDto::new).toList();
    }
}
