package com.sparta.nbcamptodo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.repository.TodoRepository;
import com.sparta.nbcamptodo.repository.UserRepository;
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

    @Test
    @DisplayName("할 일 생성 테스트")
    void test1() {
        //given
        TodoRequestDto requestDto = new TodoRequestDto("제목", "내용");
        User user = createUser();
        TodoService todoService = new TodoService(todoRepository, userRepository);
        Todo todo = new Todo(requestDto, user);
        given(todoRepository.save(any())).willReturn(todo);

        //when
        TodoDetailResponseDto responseDto = todoService.createTodo(requestDto, user);

        //then
        assertThat(responseDto.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(responseDto.getContent()).isEqualTo(requestDto.getContent());
    }

    private static User createUser() {
        return new User(new SignRequestDto("userA", "12345678"));
    }

}