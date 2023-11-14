package com.sparta.nbcamptodo.repository;

import com.sparta.nbcamptodo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
