package com.sparta.nbcamptodo.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignRequestDto {

    @Size(min = 4, max = 10, message = "4~10자를 입력해 주세요")
    @Pattern(regexp = "/^[a-z0-9]*$/", message = "알파벳 소문자와 숫자만 입력이 가능합니다.")
    private String username;

    @Size(min = 8, max = 15, message = "8~15자를 입력해 주세요")
    @Pattern(regexp = "/^[a-zA-Z0-9]*$/", message = "알파벳 대소문자와 숫자만 입력이 가능합니다.")
    private String password;

}
