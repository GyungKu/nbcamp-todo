package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoResponseDto;
import com.sparta.nbcamptodo.dto.UserRequestDto;
import com.sparta.nbcamptodo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired
    TodoService todoService;

    @Test
    void 투두_작성_테스트() {
        UserRequestDto userDto = new UserRequestDto("userA", "12345678");
        User user = new User(userDto);
        TodoRequestDto requestDto = new TodoRequestDto("제목", "내용");
        TodoResponseDto todo = todoService.createTodo(requestDto, user);

        assertThat(todo.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(todo.getContent()).isEqualTo(requestDto.getContent());
        assertThat(todo.getUsername()).isEqualTo(user.getUsername());
    }

}