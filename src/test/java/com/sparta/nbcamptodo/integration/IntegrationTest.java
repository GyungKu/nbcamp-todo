package com.sparta.nbcamptodo.integration;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.nbcamptodo.config.JpaConfig;
import com.sparta.nbcamptodo.config.QuerydslConfig;
import com.sparta.nbcamptodo.config.S3Config;
import com.sparta.nbcamptodo.dto.PageDto;
import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.SortDto;
import com.sparta.nbcamptodo.dto.TodoCondition;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.dto.TodoSearchResponseDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.repository.TodoRepository;
import com.sparta.nbcamptodo.repository.UserRepository;
import com.sparta.nbcamptodo.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;

@SpringBootTest
@Import({JpaConfig.class, S3Config.class, QuerydslConfig.class})
public class IntegrationTest {

    @Autowired
    TodoService todoService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TodoRepository todoRepository;

    @Test
    @DisplayName("할 일 검색 테스트")
    void TodoSearchTest() {
        //given
        User user = new User(new SignRequestDto("usera", "12345678"));
        Todo todo = new Todo(new TodoRequestDto("제목1", "내용1"), user);
        Todo todo2 = new Todo(new TodoRequestDto("제목2", "내용2"), user);
        Todo todo3 = new Todo(new TodoRequestDto("제목3", "내용3"), user);

        userRepository.save(user);
        todoRepository.save(todo);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        PageDto pageDto = new PageDto(1, 2);
        TodoCondition condition = new TodoCondition("제목", "내용");
        SortDto sortDto = new SortDto("createdAt", false);

        //when
        Page<TodoSearchResponseDto> todoList = todoService.getTodoListSearch(pageDto,
            sortDto, condition);

        //then
        assertThat(todoList.getContent().size()).isEqualTo(2);
        assertThat(todoList.getContent().get(0).getTitle()).isEqualTo(todo3.getTitle());
    }

}
