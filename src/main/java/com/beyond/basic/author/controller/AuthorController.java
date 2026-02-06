package com.beyond.basic.author.controller;

import com.beyond.basic.author.dto.*;
import com.beyond.basic.author.service.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@Controller
@RequestMapping("/author")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/create")
    public String CreateScreen() {
        return "author/author_create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute @Valid AuthorCreateDto dto) {
        authorService.save(dto);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginScreen() {
        return "author/author_login";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String findAll(Model model) {
        List<AuthorListDto> authorListDtoList = authorService.findAll();
        model.addAttribute("authorList", authorListDtoList);
        return "author/author_list";
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String findById(@PathVariable Long id, Model model) {
        AuthorDetailDto dto = authorService.findById(id);
        model.addAttribute("author", dto);
        return "author/author_detail";
    }
}
