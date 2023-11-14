package com.sparta.nbcamptodo.repository;

import com.sparta.nbcamptodo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
