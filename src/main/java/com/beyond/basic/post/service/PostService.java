package com.beyond.basic.post.service;

import com.beyond.basic.author.domain.Author;
import com.beyond.basic.author.repository.AuthorRepository;
import com.beyond.basic.post.domain.Post;
import com.beyond.basic.post.dto.PostCreateDto;
import com.beyond.basic.post.dto.PostDetailDto;
import com.beyond.basic.post.dto.PostListDto;
import com.beyond.basic.post.dto.PostSearchDto;
import com.beyond.basic.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PostService {
    // 레파지토리 계층 DI
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public PostService(PostRepository postRepository, AuthorRepository authorRepository) {
        this.postRepository = postRepository;
        this.authorRepository = authorRepository;
    }

    public void save(PostCreateDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getPrincipal() == null || "anonymousUser".equals(auth.getPrincipal())) {
            throw new AccessDeniedException("로그인이 필요합니다.");
        }

        String email = auth.getName(); // username을 email로 쓰는 설정일 때
        Author author = authorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("가입된 회원이 아닙니다."));

        postRepository.save(dto.toEntity(author));
    }


    @Transactional(readOnly = true)
    public Page<PostListDto> findAll(Pageable pageable, PostSearchDto postSearchDto) {
        Specification<Post> postSpecification = new Specification<Post>() {
            @Override
            public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<>();
                predicateList.add(criteriaBuilder.equal(root.get("delYn"), "NO"));
                predicateList.add(criteriaBuilder.equal(root.get("appointment"), "NO"));
                if (postSearchDto.getTitle() != null) {
                    predicateList.add(criteriaBuilder.like(root.get("title"), "%" + postSearchDto.getTitle() + "%"));
                } else if (postSearchDto.getContents() != null) {
                    predicateList.add(criteriaBuilder.like(root.get("contents"), "%" + postSearchDto.getContents() + "%"));
                } else if (postSearchDto.getCategory() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("category"), postSearchDto.getCategory()));
                }

                Predicate[] predicateArr = new Predicate[predicateList.size()];
                for (int i = 0; i < predicateArr.length; i++) {
                    predicateArr[i] = predicateList.get(i);
                }
                Predicate predicate = criteriaBuilder.and(predicateArr);
                return predicate;
            }
        };
        Page<Post> postList = postRepository.findAll(postSpecification, pageable);

        return postList.map(post -> PostListDto.fromEntity(post));
    }

    @Transactional(readOnly = true)
    public PostDetailDto findById(Long id) {

        Post post = postRepository.findByIdAndDelYn(id, "NO")
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 게시글이 없거나 삭제되었습니다."));
        return PostDetailDto.fromEntity(post);
    }

}
