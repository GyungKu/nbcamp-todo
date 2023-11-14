package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {

    private Long id;
    private String title;
    private String content;
    private String username;
    private LocalDateTime createAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.username = todo.getUser().getUsername();
        this.createAt = todo.getCreateAt();
    }
}
