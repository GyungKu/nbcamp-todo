package com.sparta.nbcamptodo.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import com.sparta.nbcamptodo.dto.CommentRequestDto;
import com.sparta.nbcamptodo.dto.CommentResponseDto;
import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.Comment;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.exception.NotFoundException;
import com.sparta.nbcamptodo.exception.UserValidationException;
import com.sparta.nbcamptodo.repository.CommentRepository;
import com.sparta.nbcamptodo.repository.TodoRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    CommentService commentService;

    User user;

    Todo todo;

    @BeforeEach
    void init() {
        user = new User(new SignRequestDto("userA", "12345678"));
        todo = new Todo(new TodoRequestDto("할 일 제목", "할 일 내용"), user);
    }

    @Nested
    @DisplayName("댓글 생성")
    class create {
        @Test
        @DisplayName("댓글 생성 실패 테스트 - 존재 하지 않는 할 일")
        void test1() {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("내용");

            //when - then
            assertThatThrownBy(() -> commentService.createComment(1L, requestDto, user))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 할 일 입니다.");
        }

        @Test
        @DisplayName("댓글 생성 성공 테스트")
        void test2() {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("댓글 내용");
            Long todoId = 1L;
            given(todoRepository.findById(todoId)).willReturn(Optional.of(todo));

            //when
            CommentResponseDto responseDto = commentService.createComment(todoId, requestDto, user);

            //then
            assertThat(responseDto.getContent()).isEqualTo(requestDto.getContent());
        }
    }

    @Nested
    @DisplayName("댓글 수정")
    class update {
        @Test
        @DisplayName("댓글 수정 실패 테스트 - 존재 하지 않는 댓글")
        void test1() {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("댓글");
            Long commentId = 1L;

            //when - then
            assertThatThrownBy(() -> commentService.updateComment(commentId, requestDto, user))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");
        }

        @Test
        @DisplayName("댓글 수정 실패 테스트 - 유저 검증 실패")
        void test2() {
            //given
            CommentRequestDto requestDto = createCommentRequestDto();
            Long commentId = 1L;
            User userB = createAnotherUser();
            Comment comment = createComment();
            given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

            //when - then
            assertThatThrownBy(() -> commentService.updateComment(commentId, requestDto, userB))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("본인의 댓글만 수정 및 삭제가 가능합니다.");
        }

        @Test
        @DisplayName("댓글 수정 성공 테스트")
        void test3() {
            //given
            CommentRequestDto requestDto = createCommentRequestDto();
            Long commentId = 1L;
            Comment comment = createComment();
            given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

            //when
            CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, user);

            //then
            assertThat(responseDto.getContent()).isEqualTo(requestDto.getContent());
        }
    }

    @Nested
    @DisplayName("댓글 삭제")
    class delete {
        @Test
        @DisplayName("댓글 삭제 실패 테스트 - 존재 하지 않는 댓글")
        void test1() {
            //given
            Long commentId = 1L;

            //when - then
            assertThatThrownBy(() -> commentService.deleteComment(commentId, user))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");
        }

        @Test
        @DisplayName("댓글 삭제 실패 테스트 - 유저 검증 실패")
        void test2() {
            //given
            Long commentId = 1L;
            User userB = createAnotherUser();
            Comment comment = createComment();
            given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

            //when - then
            assertThatThrownBy(() -> commentService.deleteComment(commentId, userB))
                .isInstanceOf(UserValidationException.class)
                .hasMessage("본인의 댓글만 수정 및 삭제가 가능합니다.");
        }

        @Test
        @DisplayName("댓글 삭제 성공 테스트")
        void test3() {
            //given
            Long commentId = 1L;
            Comment comment = createComment();
            given(commentRepository.findById(commentId)).willReturn(Optional.of(comment));

            //when
            commentService.deleteComment(commentId, user);

            //then
            assertThat(todo.getComments().size()).isEqualTo(0);
        }

    }
    private static CommentRequestDto createCommentRequestDto() {
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setContent("댓글");
        return requestDto;
    }

    private Comment createComment() {
        Comment comment = new Comment("내용", user, todo);
        return comment;
    }

    private static User createAnotherUser() {
        User userB = new User(new SignRequestDto("userB", "12345678"));
        return userB;
    }

}