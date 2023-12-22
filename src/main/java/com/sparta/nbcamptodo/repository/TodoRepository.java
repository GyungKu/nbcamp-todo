package com.sparta.nbcamptodo.repository;

import com.sparta.nbcamptodo.entity.Todo;
import com.sparta.nbcamptodo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoQueryDslRepository {
    List<Todo> findAllByUserOrderByCreatedAtDesc(User user);
}
