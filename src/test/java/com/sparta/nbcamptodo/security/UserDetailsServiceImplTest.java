package com.sparta.nbcamptodo.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.entity.User;
import com.sparta.nbcamptodo.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("유저 불러오기 실패 테스트 - 존재 하지 않는 회원")
    void test1() {
        //given
        User user = new User(new SignRequestDto("userA", "12345678"));

        //when - then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("userB"))
            .isInstanceOf(UsernameNotFoundException.class)
            .hasMessage("존재하지 않는 회원" + "userB");
    }

    @Test
    @DisplayName("유저 불러오기 성공 테스트")
    void test2() {
        //given
        User user = new User(new SignRequestDto("userA", "12345678"));
        given(userRepository.findByUsername(any())).willReturn(Optional.of(user));

        //when
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());

        //then
        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getPassword()).isEqualTo(user.getPassword());
    }

}