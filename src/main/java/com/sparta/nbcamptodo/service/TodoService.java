package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.PageDto;
import com.sparta.nbcamptodo.dto.TodoCondition;
import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoSearchResponseDto;
import com.sparta.nbcamptodo.entity.User;
import java.util.List;
import org.springframework.data.domain.Page;

public interface TodoService {

    TodoDetailResponseDto createTodo(TodoRequestDto requestDto, User user);

    TodoDetailResponseDto getTodo(Long todoId);

    List<TodoListResponseDto> getTodoList();

    TodoDetailResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, User user);

    void completedTodo(Long todoId, User user, Boolean complete);

    Page<TodoSearchResponseDto> getTodoListSearch(PageDto pageDto, TodoCondition condition);
}
