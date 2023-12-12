package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.User;
import java.util.List;

public interface TodoService {

    TodoDetailResponseDto createTodo(TodoRequestDto requestDto, User user);

    TodoDetailResponseDto getTodo(Long todoId);

    List<TodoListResponseDto> getTodoList();

    TodoDetailResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, User user);

    void completedTodo(Long todoId, User user, Boolean complete);

}
