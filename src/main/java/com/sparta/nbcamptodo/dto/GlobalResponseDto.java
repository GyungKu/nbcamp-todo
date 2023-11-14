package com.sparta.nbcamptodo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GlobalResponseDto<T> {

    private String message;
    private T data;

}
