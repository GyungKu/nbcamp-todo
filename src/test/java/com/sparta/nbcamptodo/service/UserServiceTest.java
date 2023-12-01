package com.sparta.nbcamptodo.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 성공 테스트")
    void test1() {
        // given
        SignRequestDto requestDto = new SignRequestDto("userA", "12345678");
        UserService userService = new UserService(userRepository, passwordEncoder);

        // when
        String message = userService.signup(requestDto);

        // then
        assertThat(message).isEqualTo("회원 가입 성공");
    }

}