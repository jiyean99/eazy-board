package com.beyond.basic.author.dto;

import com.beyond.basic.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorCreateDto {
    @NotBlank(message = "이름을 작성하시오.")
    private String name;
    @NotBlank(message = "이메일을 작성하시오.")
    private String email;
    @NotBlank(message = "비밀번호를 작성하시오.")
    @Size(min = 8, message = "비밀번호의 길이가 짧습니다. 8자 이상 입력하시오.")
    private String password;

    public Author toEntity(String encodedPassword) {
        return Author.builder()
                .name(this.name)
                .email(this.email)
                .password(encodedPassword)
                .build();
    }
}
