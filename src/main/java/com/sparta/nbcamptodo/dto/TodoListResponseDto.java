package com.sparta.nbcamptodo.dto;

import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class TodoListResponseDto {
    private UserResponseDto user;
    private List<TodoResponseDto> todoList = new ArrayList<>();

    public TodoListResponseDto(User user, List<Todo> todos) {
        this.user = new UserResponseDto(user);
        for (Todo todo : todos) {
            todoList.add(new TodoResponseDto(todo));
        }
    }
}
