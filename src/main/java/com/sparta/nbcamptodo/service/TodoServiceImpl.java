package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.PageDto;
import com.sparta.nbcamptodo.dto.SortDto;
import com.sparta.nbcamptodo.dto.TodoCondition;
import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoSearchResponseDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.exception.NotFoundException;
import com.sparta.nbcamptodo.exception.UserValidationException;
import com.sparta.nbcamptodo.repository.TodoQueryDslRepository;
import com.sparta.nbcamptodo.repository.TodoRepository;
import com.sparta.nbcamptodo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;
    private final TodoQueryDslRepository todoQueryDslRepository;

    @Transactional
    public TodoDetailResponseDto createTodo(TodoRequestDto requestDto, User user) {
        Todo todo = todoRepository.save(new Todo(requestDto, user));
        return new TodoDetailResponseDto(todo);
    }

    public TodoDetailResponseDto getTodo(Long todoId) {
        Todo todo = findTodo(todoId);
        TodoDetailResponseDto responseDto = new TodoDetailResponseDto(todo);
        return responseDto;
    }

    public List<TodoListResponseDto> getTodoList() {
        List<User> users = userRepository.findAll();
        List<TodoListResponseDto> todoList = new ArrayList<>();

        /**
         * 유저 테이블을 양방향으로 걸어서 처리하려 했으나,
         * named query를 통해 안에있는 todoList만 내림차순 정렬하는 방법을 몰라서
         * 모든 유저를 뽑아온 후, 유저별로 todoList를 뽑고 responseDto에 넣어주는 방식을 선택함
         */
        for (User user : users) {
            List<Todo> todos = todoRepository.findAllByUserOrderByCreatedAtDesc(user);
            todoList.add(new TodoListResponseDto(user, todos));
        }

        return todoList;
    }

    @Override
    public Page<TodoSearchResponseDto> getTodoListSearch(PageDto pageDto, SortDto sortDto,
        TodoCondition condition) {
        Page<Todo> todoList = todoQueryDslRepository.searchTodoList(condition, pageDto.toPageable(),
            sortDto);
       return todoList.map(todo -> new TodoSearchResponseDto(todo, todo.getUser()));
    }

    @Transactional
    public TodoDetailResponseDto updateTodo(Long todoId, TodoRequestDto requestDto, User user) {
        Todo todo = userValidation(user.getUsername(), todoId);
        todo.update(requestDto.getTitle(), requestDto.getContent());
        return new TodoDetailResponseDto(todo);
    }

    @Transactional
    public void completedTodo(Long todoId, User user, Boolean complete) {
        Todo todo = userValidation(user.getUsername(), todoId);
        todo.completed(complete);
    }

    private Todo findTodo(Long todoId) {
        return todoRepository.findById(todoId).orElseThrow(() -> new NotFoundException("존재하지 않는 할 일 입니다."));
    }

    private Todo userValidation(String username, Long todoId) {
        Todo todo = findTodo(todoId);
        if (!todo.getUser().getUsername().equals(username)) {
            throw new UserValidationException("본인의 할 일만 수정이 가능합니다.");
        }
        return todo;
    }

}
