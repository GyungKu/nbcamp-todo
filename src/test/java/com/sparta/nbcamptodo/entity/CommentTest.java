package com.sparta.nbcamptodo.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommentTest {

    @Test
    @DisplayName("댓글 수정 테스트")
    void test1() {
        User user = createUser();
        Todo todo = createTodo(user);
        Comment comment = createComment(user, todo);

        comment.update("내용 수정");

        assertThat(comment.getContent()).isEqualTo("내용 수정");
    }

    private static Comment createComment(User user, Todo todo) {
        return new Comment("내용", user, todo);
    }


    private static Todo createTodo(User user) {
        return new Todo(new TodoRequestDto("제목", "내용"), user);
    }

    private static User createUser() {
        return new User(new SignRequestDto("userA", "12345678"));
    }

}