package com.sparta.nbcamptodo.controller;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoDetailResponseDto;
import com.sparta.nbcamptodo.dto.TodoListResponseDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.exception.NotFoundException;
import com.sparta.nbcamptodo.exception.UserValidationException;
import com.sparta.nbcamptodo.mockSecurity.MockSpringSecurityFilter;
import com.sparta.nbcamptodo.security.UserDetailsImpl;
import com.sparta.nbcamptodo.service.TodoService;
import java.security.Principal;
import java.util.Arrays;
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

@WebMvcTest(controllers = TodoController.class)
class TodoControllerTest {

    @Autowired
    MockMvc mvc;

    Principal principal;

    @MockBean
    TodoService todoService;

    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    User user;

    @BeforeEach
    void setup() {
        user = new User(new SignRequestDto("userA", "12345678"));
        UserDetailsImpl userDetails = new UserDetailsImpl(user);

        principal = new UsernamePasswordAuthenticationToken(
            userDetails, "", userDetails.getAuthorities()
        );

        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();
    }

    @Nested
    @DisplayName("할 일 생성")
    class create {
        @Test
        @DisplayName("할 일 생성 성공 테스트")
        void test1() throws Exception {
            //given
            TodoRequestDto requestDto = new TodoRequestDto("할 일 제목", "할 일 내용");
            String todoRequest = objectMapper.writeValueAsString(requestDto);
            Todo todo = new Todo(requestDto, user);
            given(todoService.createTodo(any(), any())).willReturn(new TodoDetailResponseDto(todo));


            //when - then
            mvc.perform(post("/api/todo")
                    .content(todoRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isCreated())
                .andDo(print());

            verify(todoService).createTodo(any(), any());
        }
    }

    @Nested
    @DisplayName("할 일 조회")
    class read {
        @Test
        @DisplayName("상세 조회")
        void test1() throws Exception {
            //given
            Todo todo = new Todo(new TodoRequestDto("제목", "내용"), user);
            TodoDetailResponseDto responseDto = new TodoDetailResponseDto(todo);
            given(todoService.getTodo(any())).willReturn(responseDto);

            //when - then
            mvc.perform(get("/api/todo/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("제목"))
                .andExpect(jsonPath("$.data.content").value("내용"))
                .andDo(print());

            verify(todoService).getTodo(any());
        }
        
        @Test
        @DisplayName("목록 조회")
        void test2() throws Exception {
            //given
            Todo todo = new Todo(new TodoRequestDto("제목", "내용"), user);
            TodoListResponseDto responseDto = new TodoListResponseDto(user, Arrays.asList(todo));
            given(todoService.getTodoList()).willReturn(Arrays.asList(responseDto));

            //when - then
            mvc.perform(get("/api/todoList"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data[0].user.username").value("userA"))
                .andExpect(jsonPath("$.data[0].todoList[0].title").value("제목"))
                .andDo(print());

            verify(todoService).getTodoList();
        }

        @Test
        @DisplayName("상세 조회 실패 테스트 - 존재하지 않는 할 일")
        void test3() throws Exception {
            //given
            given(todoService.getTodo(any())).willThrow(new NotFoundException("존재하지 않는 할 일 입니다."));

            //when
            mvc.perform(get("/api/todo/1"))
                .andExpect(status().isBadRequest())
                .andDo(print());

            //then
            verify(todoService).getTodo(any());
        }
    }

    @Nested
    @DisplayName("할 일 수정")
    class update {
        @Test
        @DisplayName("수정 테스트")
        void test1() throws Exception {
            //given
            TodoRequestDto requestDto = new TodoRequestDto("제목 수정", "내용 수정");
            given(todoService.updateTodo(any(), any(), any()))
                .willReturn(new TodoDetailResponseDto(new Todo(requestDto, user)));

            //when - then
            mvc.perform(patch("/api/todo/1")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("제목 수정"))
                .andExpect(jsonPath("$.data.content").value("내용 수정"))
                .andDo(print());

            verify(todoService).updateTodo(any(), any(), any());
        }

        @Test
        @DisplayName("완료 테스트")
        void test2() throws Exception {
            //when - then
            mvc.perform(post("/api/todo/1?complete=true")
                    .principal(principal))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("할 일 완료 처리 성공"))
                .andExpect(jsonPath("$.data").value("succeed todo complete update"))
                .andDo(print());
        }

        @Test
        @DisplayName("할 일 수정 실패 테스트 - 본인의 할 일이 아님")
        void test3() throws Exception {
            //given
            TodoRequestDto requestDto = new TodoRequestDto("제목 수정", "내용 수정");
            given(todoService.updateTodo(any(), any(), any()))
                .willThrow(new UserValidationException("본인의 할 일만 수정이 가능합니다."));

            //when - then
            mvc.perform(patch("/api/todo/1")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());

            verify(todoService).updateTodo(any(), any(), any());
        }

        @Test
        @DisplayName("할 일 수정 실패 테스트 - 존재하지 않는 할 일")
        void test4() throws Exception {
            //given
            TodoRequestDto requestDto = new TodoRequestDto("제목 수정", "내용 수정");
            given(todoService.updateTodo(any(), any(), any()))
                .willThrow(new NotFoundException("존재하지 않는 할 일 입니다."));

            //when
            mvc.perform(patch("/api/todo/1")
                    .content(objectMapper.writeValueAsString(requestDto))
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .principal(principal))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.data").exists())
                .andDo(print());

            verify(todoService).updateTodo(any(), any(), any());
        }
    }

}