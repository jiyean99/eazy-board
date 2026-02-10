package com.beyond.basic.author;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.dto.AuthorCreateDto;
import com.beyond.basic.author.repository.AuthorRepository;
import com.beyond.basic.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class AuthorServiceTest {
    private final AuthorService authorService;
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceTest(AuthorService authorService, AuthorRepository authorRepository) {
        this.authorService = authorService;
        this.authorRepository = authorRepository;
    }

    @Test
    public void authorSaveTest() {
//        given
        AuthorCreateDto dto = AuthorCreateDto.builder()
                .name("abc")
                .email("abc@naver.com")
                .password("12341234")
                .build();
//        when
        authorService.save(dto);
//        then
        Author authorDb=authorRepository.findByEmail("abc@naver.com").orElse(null);
        Assertions.assertEquals(authorDb.getEmail(), dto.getEmail());
    }

    @Test
    public void authorFindAllTest() {
//        findAll을 통한 결과값의 개수가 맞는지를 검증
//        최초개수 구하고
        int beforeSize = authorService.findAll().size();
//        데이터 추가(3개)
        authorRepository.save(Author.builder().name("h1").email("h1@naver.com").password("12341234").build());
        authorRepository.save(Author.builder().name("h2").email("h2@naver.com").password("12341234").build());
        authorRepository.save(Author.builder().name("h3").email("h3@naver.com").password("12341234").build());
//        db에서 findAll을 통해 총 개수를 구하여 최초개수 +3하고 일치하는지 검증
        int afterSize = authorService.findAll().size();
        Assertions.assertEquals(beforeSize+3, afterSize);
    }
}
