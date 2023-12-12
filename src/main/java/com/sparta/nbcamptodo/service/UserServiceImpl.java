package com.sparta.nbcamptodo.service;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.exception.DuplicateUsernameException;
import com.sparta.nbcamptodo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup(SignRequestDto requestDto) {
        Optional<User> findUser = userRepository.findByUsername(requestDto.getUsername());
        if (findUser.isPresent()) {
            throw new DuplicateUsernameException("중복 username 입니다.");
        }
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        userRepository.save(new User(requestDto));
        return "회원 가입 성공";
    }
}
