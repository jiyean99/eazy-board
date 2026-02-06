package com.beyond.basic.post.controller;

import com.beyond.basic.post.dto.PostCreateDto;
import com.beyond.basic.post.dto.PostDetailDto;
import com.beyond.basic.post.dto.PostListDto;
import com.beyond.basic.post.dto.PostSearchDto;
import com.beyond.basic.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@Controller
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public String homeScreen(){
        return "common/home";
    }

    @GetMapping("/post/create")
    public String postCreateScreen(){
        return "post/post_create";
    }

    @PostMapping("/post/create")
    public String postCreate(@ModelAttribute @Valid PostCreateDto dto) {
        postService.save(dto);
        return "redirect:/posts";
    }

    @GetMapping("/posts")
    public String postListDto(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                                         @ModelAttribute PostSearchDto postSearchDto, Model model) {

        Page<PostListDto> postListDtos = postService.findAll(pageable,postSearchDto);

        model.addAttribute("postList", postListDtos);
        return "post/post_list";
    }

    @GetMapping("/post/{id}")
    public String postDetail(@PathVariable Long id, Model model) {
        PostDetailDto dto = postService.findById(id);
        model.addAttribute("postDetail", dto);
        return "post/post_detail";
    }

}
