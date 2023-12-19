package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {

    private Long id;
    private String title;
    private Boolean complete;
    private LocalDateTime createdAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.complete = todo.getComplete();
        this.createdAt = todo.getCreatedAt();
    }
}
