package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String username;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
