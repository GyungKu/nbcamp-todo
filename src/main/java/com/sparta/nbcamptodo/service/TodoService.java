package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoResponseDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.repository.TodoRepository;
import com.sparta.nbcamptodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.save(new Todo(requestDto, user));
        return new TodoResponseDto(todo);
    }

    public TodoResponseDto getTodo(Long todoId) {
        Todo todo = findTodo(todoId);
        TodoResponseDto responseDto = new TodoResponseDto(todo);
        return responseDto;
    }

    public List<TodoListResponseDto> getTodoList() {
        List<User> users = userRepository.findAll();
        List<TodoListResponseDto> todoList = new ArrayList<>();
        for (User user : users) {
            List<Todo> todos = todoRepository.findAllByUserOrderByCreateAtDesc(user);
            todoList.add(new TodoListResponseDto(user, todos));
        }

        return todoList;
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, User user) {
        Todo todo = findTodo(todoId);
        User findUser = todo.getUser();
        if (findUser.getId() != user.getId()) {
            throw new IllegalArgumentException("본인의 할 일만 수정이 가능합니다.");
        }
        todo.update(requestDto.getTitle(), requestDto.getContent());
        return new TodoResponseDto(todo);
    }

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 할 일 입니다."));
    }
}
