package com.beyond.basic.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PostDeleteDto {
    private Long id;
    private String title;
    private String contents;
    private String category;
    private String authorEmail;
    private String delYn;
}
