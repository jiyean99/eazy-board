package com.beyond.basic.post.dto;

import com.beyond.basic.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostListDto {
    private Long id;
    private String title;
    private String category;
    private String authorEmail;

    public static PostListDto fromEntity(Post post) {
        return PostListDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .category(post.getCategory())
                .authorEmail(post.getAuthor().getEmail())
                .build();
    }

}
