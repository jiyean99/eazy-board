package com.beyond.basic.post.domain;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.common.domain.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(length = 3000)
    private String contents;
    private String category;
    @Builder.Default
    private String delYn = "NO";

    @Builder.Default
    private String appointment = "NO";
    @Builder.Default
    private LocalDateTime appointmentTime = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT), nullable = false)
    private Author author;

}
