package com.sparta.nbcamptodo.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDto {

    private String title;

    private String content;

    private LocalDateTime createAt;

    public TodoRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
