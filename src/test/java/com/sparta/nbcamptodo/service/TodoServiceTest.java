package com.sparta.nbcamptodo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.exception.NotFoundException;
import com.sparta.nbcamptodo.repository.TodoRepository;
import com.sparta.nbcamptodo.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    TodoRepository todoRepository;

    @Mock
    UserRepository userRepository;

    TodoService todoService;

    User user;

    @BeforeEach
    void init() {
        user = new User(new SignRequestDto("userA", "12345678"));
        todoService = new TodoService(todoRepository, userRepository);
    }

    @Test
    @DisplayName("할 일 생성 테스트")
    void test1() {
        //given
        TodoRequestDto requestDto = new TodoRequestDto("제목", "내용");
        Todo todo = new Todo(requestDto, user);
        given(todoRepository.save(any())).willReturn(todo);

        //when
        TodoDetailResponseDto responseDto = todoService.createTodo(requestDto, user);

        //then
        assertThat(responseDto.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(requestDto.getContent());
    }

    @Test
    @DisplayName("할일 상세 조회 성공 테스트")
    void test2() {
        //given
        TodoRequestDto requestDto = new TodoRequestDto("제목", "내용");
        Todo todo = new Todo(requestDto, user);
        given(todoRepository.findById(any())).willReturn(Optional.of(todo));

        //when
        TodoDetailResponseDto responseDto = todoService.getTodo(1L);

        //then
        assertThat(responseDto.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(requestDto.getContent());
    }

    @Test
    @DisplayName("할 일 상세 조회 실패 테스트")
    void test3() {
        //given
        Long todoId = 1L;

        //when - then
        assertThatThrownBy(() -> todoService.getTodo(todoId)).isInstanceOf(NotFoundException.class)
            .hasMessage("존재하지 않는 할 일 입니다.");
    }

    @Test
    @DisplayName("유저 별 할 일 목록 조회 테스트")
    void test4() {
        //given
        List<User> users = new ArrayList<>();
        users.add(user);
        TodoRequestDto requestDto = new TodoRequestDto("제목", "내용");
        List<Todo> todos = new ArrayList<>();
        Todo todo = new Todo(requestDto, user);
        todos.add(todo);
        given(userRepository.findAll()).willReturn(users);
        given(todoRepository.findAllByUserOrderByCreateAtDesc(any())).willReturn(todos);

        //when
        List<TodoListResponseDto> todoList = todoService.getTodoList();

        //then
        assertThat(todoList.size()).isEqualTo(1);
        assertThat(todoList.get(0).getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(todoList.get(0).getTodoList().get(0).getTitle()).isEqualTo(requestDto.getTitle());
    }

    private static User createUser() {
        return new User(new SignRequestDto("userA", "12345678"));
    }

}