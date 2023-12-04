package com.sparta.nbcamptodo.repository;

import static org.assertj.core.api.Assertions.*;

import com.sparta.nbcamptodo.dto.SignRequestDto;
import com.sparta.nbcamptodo.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    @DisplayName("유저 DB 저장 테스트")
    void test1() {
        //given
        User user = new User(new SignRequestDto("user1", "12345678"));

        //when
        User saveUser = userRepository.save(user);

        //then
        assertThat(saveUser.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    @DisplayName("유저 이름으로 데이터 찾기")
    void test2() {
        //given
        User user = new User(new SignRequestDto("user1", "12345678"));
        userRepository.save(user);

        //when
        User findUser = userRepository.findByUsername(user.getUsername()).get();

        //then
        assertThat(findUser.getUsername()).isEqualTo(user.getUsername());
    }

}
