package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoDetailResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private Boolean complete;
    private LocalDateTime createAt;

    public TodoDetailResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.username = todo.getUser().getUsername();
        this.complete = todo.getComplete();
        this.createAt = todo.getCreateAt();
    }
}
