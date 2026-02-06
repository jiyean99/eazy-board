package com.beyond.basic.author.dto;

import com.beyond.basic.author.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorDetailDto {
    private Long id;
    private String name;
    private String email;
    private int postCount;

    public static AuthorDetailDto fromEntity (Author author){
        return AuthorDetailDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .postCount(author.getPostList().size())
                .build();
    }
}
