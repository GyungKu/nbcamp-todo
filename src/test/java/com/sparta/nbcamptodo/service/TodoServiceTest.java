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

    @Test
    void 상세_조회_테스트() {
        // 회원가입
        SignRequestDto signDto = new SignRequestDto("user1", "1234");
        userService.signup(signDto);

        // 유저 갖고 오기
        User user = userRepository.findByUsername(signDto.getUsername()).get();

        // 할 일 작성
        TodoRequestDto requestDto = new TodoRequestDto("제목", "내용");
        TodoResponseDto todoResponseDto = todoService.createTodo(requestDto, user);

        // 작성한 할 일 갖고 오기
        TodoResponseDto todo = todoService.getTodo(todoResponseDto.getId());

        // 작성한 것과 갖고온 것이 같은지 확인 해보기
        assertThat(todo.getTitle()).isEqualTo(todoResponseDto.getTitle());
        assertThat(todo.getUsername()).isEqualTo(todoResponseDto.getUsername());
        assertThat(todo.getId()).isEqualTo(todoResponseDto.getId());
        assertThat(todo.getContent()).isEqualTo(todoResponseDto.getContent());
    }

}