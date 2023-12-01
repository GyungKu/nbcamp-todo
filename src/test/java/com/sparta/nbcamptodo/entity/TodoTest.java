package com.sparta.nbcamptodo.entity;

import static org.junit.jupiter.api.Assertions.*;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TodoTest {

    @Test
    @DisplayName("할 일 수정 테스트")
    void test1() {
        // given
        User user = createUser();
        Todo todo = createTodo(user);

        // when
        todo.update("제목 수정", "내용 수정");

        // then
        Assertions.assertThat(todo.getTitle()).isEqualTo("제목 수정");
        Assertions.assertThat(todo.getContent()).isEqualTo("내용 수정");
    }

    private static User createUser() {
        return new User(new SignRequestDto("userA", "12345678"));
    }

    private static Todo createTodo(User user) {
        return new Todo(new TodoRequestDto("제목", "내용"), user);
    }

}