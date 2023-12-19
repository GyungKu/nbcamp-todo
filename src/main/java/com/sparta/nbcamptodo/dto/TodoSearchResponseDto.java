package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodoSearchResponseDto {

    private Long id;
    private String title;
    private Boolean complete;
    private LocalDateTime createdAt;
    private String username;
    private Long userId;

    public TodoSearchResponseDto(Todo todo, User user) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.complete = todo.getComplete();
        this.createdAt = todo.getCreatedAt();
        this.username = user.getUsername();
        this.userId = user.getId();
    }
}
