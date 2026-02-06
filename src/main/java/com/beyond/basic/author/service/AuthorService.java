package com.beyond.basic.author.service;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.dto.*;
import com.beyond.basic.author.repository.AuthorRepository;
import com.beyond.basic.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final PostRepository postRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthorService(AuthorRepository authorRepository, PostRepository postRepository, PasswordEncoder passwordEncoder) {
        this.authorRepository = authorRepository;
        this.postRepository = postRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void save(AuthorCreateDto dto) {
        Author author = dto.toEntity(passwordEncoder.encode(dto.getPassword()));
        if (authorRepository.findByEmail(author.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        authorRepository.save(author);
    }

    public Author login(AuthorLoginDto dto) {
        Optional<Author> optionalAuthor = authorRepository.findByEmail(dto.getEmail());

        boolean check = true;

        if (!optionalAuthor.isPresent()) {
            check = false;
        } else {
            if (!passwordEncoder.matches(dto.getPassword(), optionalAuthor.get().getPassword())) {
                check = false;
            }
        }

        if (!check) {
            throw new IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.");
        }

        return optionalAuthor.get();
    }

    @Transactional(readOnly = true)
    public List<AuthorListDto> findAll() {
        return authorRepository
                .findAll()
                .stream()
                .map(a -> AuthorListDto.fromEntity(a))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorDetailDto findById(Long id) {
        Optional<Author> optionalAuthor = authorRepository.findById(id);

        Author author = optionalAuthor.orElseThrow(
                () -> new NoSuchElementException("Entity is not found")
        );
        return AuthorDetailDto.fromEntity(author);
    }
}
