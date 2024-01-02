package com.sparta.nbcamptodo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.nbcamptodo.config.JpaConfig;
import com.sparta.nbcamptodo.config.QuerydslConfig;
import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.SortDto;
import com.sparta.nbcamptodo.dto.TodoCondition;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({JpaConfig.class, QuerydslConfig.class})
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    UserRepository userRepository;
    
    @Test
    @DisplayName("유저별 할 일 목록 조회 테스트")
    void test1() {
        //given
        User user = new User(new SignRequestDto("user1", "12345678"));
        userRepository.save(user);
        Todo todo1 = new Todo(new TodoRequestDto(
            "제목1", "내용1"), user);
        Todo todo2 = new Todo(new TodoRequestDto(
                "제목2", "내용2"), user);
        Todo todo3 = new Todo(new TodoRequestDto(
            "제목3", "내용3"), user);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        System.out.println("todo1.getCreatedAt() = " + todo1.getCreatedAt());

        //when
        List<Todo> todoList = todoRepository.findAllByUserOrderByCreatedAtDesc(
            user);

        //then
        assertThat(todoList.get(0).getTitle()).isEqualTo(todo3.getTitle());
        assertThat(todoList.get(1).getTitle()).isEqualTo(todo2.getTitle());
        assertThat(todoList.get(2).getTitle()).isEqualTo(todo1.getTitle());
    }

    @Test
    @DisplayName("검색 테스트")
    void searchTest() {
        //given
        User user = new User(new SignRequestDto("user1", "12345678"));
        userRepository.save(user);
        Todo todo1 = new Todo(new TodoRequestDto(
            "제목1", "내용1"), user);
        Todo todo2 = new Todo(new TodoRequestDto(
            "제목2", "내용2"), user);
        Todo todo3 = new Todo(new TodoRequestDto(
            "제목3", "내용3"), user);
        todoRepository.save(todo1);
        todoRepository.save(todo2);
        todoRepository.save(todo3);

        // codeDeploy 테스트 주석
        /**
         * and가 아닌 or이므로, 제목, 내용 둘 중 하나라도 일치하면 꺼내온다.
         * 아래서 꺼내오는 데이터는 2개, 제목이 제목1인 것과 내용이 내용2인 것
         * 생성일 기준으로 내림차순 정렬 하기에 내용2가 먼저, 제목1이 다음이다.
         */
        TodoCondition cond = new TodoCondition("제목1", "내용2");
        Pageable pageable = PageRequest.of(0, 2);
        SortDto sortDto = new SortDto("createdAt", false);

        //when
        Page<Todo> todos = todoRepository.searchTodoList(cond, pageable, sortDto);
        List<Todo> todoList = todos.getContent();

        //then
        assertThat(todos.getTotalElements()).isEqualTo(2);
        assertThat(todoList.get(0).getTitle()).isEqualTo(todo2.getTitle());
        assertThat(todoList.get(1).getContent()).isEqualTo(todo1.getContent());
    }
    
}
