package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoResponseDto;
import com.sparta.nbcamptodo.dto.LoginRequestDto;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class TodoServiceTest {

    @Autowired
    TodoService todoService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Test
    void 투두_작성_테스트() {
        SignRequestDto signDto = new SignRequestDto("user1", "1234");
        userService.signup(signDto);
        User user = userRepository.findByUsername(signDto.getUsername()).get();
        TodoRequestDto requestDto = new TodoRequestDto("제목", "내용");
        TodoResponseDto todo = todoService.createTodo(requestDto, user);

        assertThat(todo.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(todo.getContent()).isEqualTo(requestDto.getContent());
        assertThat(todo.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    void 회원가입_테스트() {
        SignRequestDto signDto = new SignRequestDto("user1", "1234");
        userService.signup(signDto);
        User user = userRepository.findByUsername(signDto.getUsername()).get();
        assertThat(user.getUsername()).isEqualTo(signDto.getUsername());
    }

    @Test
    void 회원가입_중복_회원_테스트() {
        SignRequestDto signDto = new SignRequestDto("user1", "1234");
        userService.signup(signDto);

        assertThatThrownBy(() -> userService.signup(new SignRequestDto("user1", "1234")))
                .isInstanceOf(IllegalArgumentException.class);
    }

}