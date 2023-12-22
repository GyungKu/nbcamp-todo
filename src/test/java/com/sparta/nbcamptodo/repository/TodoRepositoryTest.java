package com.sparta.nbcamptodo.repository;

import static org.assertj.core.api.Assertions.*;

import com.sparta.nbcamptodo.config.JpaConfig;
import com.sparta.nbcamptodo.config.QuerydslConfig;
import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.dto.TodoRequestDto;
import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
    
}
