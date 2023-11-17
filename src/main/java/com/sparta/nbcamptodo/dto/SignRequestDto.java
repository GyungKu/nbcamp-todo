package com.sparta.nbcamptodo.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignRequestDto {

    /**
     * 패턴을 통해 길이와 패턴 모두 검증하면 각각 따로 메세지를 보낼 수 없어서 groups 와 sequence 를 통해 해결 하려고 했으나,
     * 그렇게 하면 username 과 password 모두 size 를 통과해야 pattern 으로 넘어간다.
     * 즉, username 은 size 를 통과하고 pattern 은 통과 하지 못 한 상태, password 는 size도 통과를 못 한 상태라면
     * username 의 pattern 에러는 표기되지 않는다. password 의 size에러만 표기 된다.
     */
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "4 ~ 10자, 알파벳 소문자와 숫자만 입력이 가능합니다.")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "8 ~ 15자, 알파벳 대소문자와 숫자만 입력이 가능합니다.")
    private String password;

}
