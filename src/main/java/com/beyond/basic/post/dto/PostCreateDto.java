package com.beyond.basic.post.dto;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PostCreateDto {
    @NotBlank(message = "제목을 작성하시오.")
    private String title;
    @NotBlank(message = "본문을 작성하시오.")
    @Size(max = 3000, message = "3000자 이하로 작성하시오.")
    private String contents;
    private String delYn;
    @Builder.Default
    private String appointment = "NO";
    @Builder.Default
    private LocalDateTime appointmentTime = LocalDateTime.now();

    public Post toEntity(Author authorByEmail) {
        return Post.builder()
                .author(authorByEmail)
                .title(this.title)
                .contents(this.contents)
                .appointment(this.appointment)
                .appointmentTime(this.appointmentTime)
                .build();
    }
}
