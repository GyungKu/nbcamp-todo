package com.sparta.nbcamptodo.repository;

import com.sparta.nbcamptodo.dto.SortDto;
import com.sparta.nbcamptodo.dto.TodoCondition;
import com.sparta.nbcamptodo.entity.Todo;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoQueryDslRepository {

    Page<Todo> searchTodoList(TodoCondition condition, Pageable pageable, SortDto sortDto);

}
