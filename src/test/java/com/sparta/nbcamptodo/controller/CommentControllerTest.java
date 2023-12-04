package com.sparta.nbcamptodo.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.verify;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nbcamptodo.dto.CommentRequestDto;
import com.sparta.nbcamptodo.dto.CommentResponseDto;
import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.Comment;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.exception.NotFoundException;
import com.sparta.nbcamptodo.exception.UserValidationException;
import com.sparta.nbcamptodo.mockSecurity.MockSpringSecurityFilter;
import com.sparta.nbcamptodo.security.UserDetailsImpl;
import com.sparta.nbcamptodo.service.CommentService;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = CommentController.class)
class CommentControllerTest {

    @Autowired
    MockMvc mvc;

    Principal principal;

    @MockBean
    CommentService commentService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    User user;

    Todo todo;

    @BeforeEach
    void setup() {
        user = new User(new SignRequestDto("userA", "12345678"));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        todo = new Todo(new TodoRequestDto("할 일 제목", "할 일 내용"), user);

        principal = new UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.getAuthorities()
        );

        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }

    @Nested
    @DisplayName("댓글 생성")
    class create {
        @Test
        @DisplayName("댓글 생성 테스트")
        void test1() throws Exception {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("내용");
            CommentResponseDto responseDto = new CommentResponseDto(
                new Comment("내용", user, todo));
            given(commentService.createComment(any(), any(), any())).willReturn(responseDto);

            //when - then
            mvc.perform(post("/api/todo/1/comment")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.content").value("내용"))
                .andDo(print());

            verify(commentService).createComment(any(), any(), any());
        }

        @Test
        @DisplayName("댓글 생성 실패 테스트 - 존재하지 않는 할 일")
        void test2() throws Exception {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("내용");
            CommentResponseDto responseDto = new CommentResponseDto(
                new Comment("내용", user, todo));
            given(commentService.createComment(any(), any(), any()))
                .willThrow(new NotFoundException("존재하지 않는 할 일 입니다."));

            //when - then
            mvc.perform(post("/api/todo/1/comment")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isBadRequest())
                .andDo(print());

            verify(commentService).createComment(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("댓글 수정")
    class update {
        @Test
        @DisplayName("댓글 수정 테스트")
        void test1() throws Exception {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("수정");
            given(commentService.updateComment(any(), any(), any()))
                .willReturn(new CommentResponseDto(new Comment("수정", user, todo)));

            //when - then
            mvc.perform(patch("/api/todo/comment/1")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").value("수정"))
                .andDo(print());

            verify(commentService).updateComment(any(), any(), any());
        }

        @Test
        @DisplayName("댓글 수정 실패 테스트 - 본인의 댓글이 아님")
        void test2() throws Exception {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("수정");
            given(commentService.updateComment(any(), any(), any()))
                .willThrow(new UserValidationException("본인의 댓글만 수정 및 삭제가 가능합니다."));

            //when - then
            mvc.perform(patch("/api/todo/comment/1")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isBadRequest())
                .andDo(print());

            verify(commentService).updateComment(any(), any(), any());
        }

        @Test
        @DisplayName("댓글 수정 실패 테스트 - 존재하지 않는 댓글")
        void test3() throws Exception {
            //given
            CommentRequestDto requestDto = new CommentRequestDto();
            requestDto.setContent("수정");
            given(commentService.updateComment(any(), any(), any()))
                .willThrow(new NotFoundException("존재하지 않는 댓글 입니다."));

            //when - then
            mvc.perform(patch("/api/todo/comment/1")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data").value("존재하지 않는 댓글 입니다."))
                .andDo(print());

            verify(commentService).updateComment(any(), any(), any());
        }

    }

    @Nested
    @DisplayName("댓글 삭제")
    class delete {
        @Test
        @DisplayName("댓글 삭제 테스트")
        void test1() throws Exception {
            //when - then
            mvc.perform(delete("/api/todo/comment/1")
                    .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());

            verify(commentService).deleteComment(any(), any());
        }

    }

}