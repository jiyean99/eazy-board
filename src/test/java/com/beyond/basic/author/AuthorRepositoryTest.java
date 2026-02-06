package com.beyond.basic.author;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.repository.AuthorRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AuthorRepositoryTest {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Test
    public void authorSaveTest() {
        // 테스트 검증 로직: 객체 생성(저장 전) -> save -> 저장된 객체(DB 조회)를 조회하여 저장전의 객체와 비교
        // - given when then 패턴(prepare execute then 패턴)

        // (1) 준비(prepare, given)
        Author author = Author.builder()
                .name("abc")
                .email("abc@naver.com")
                .password("12341234")
                .build();

        // (2) 실행(execute, when)
        authorRepository.save(author);

        // (3)검증(then)
        Author authorDb = authorRepository.findByEmail("abc@naver.com").orElse(null);
        Assertions.assertEquals(author.getEmail(), authorDb.getEmail());
    }
}
