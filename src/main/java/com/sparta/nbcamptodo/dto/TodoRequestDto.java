package com.sparta.nbcamptodo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDto {

    private String title;

    private String content;

}
