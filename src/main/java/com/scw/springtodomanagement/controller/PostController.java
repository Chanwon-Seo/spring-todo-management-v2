package com.scw.springtodomanagement.controller;

import com.scw.springtodomanagement.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Tag(name = "02. Post")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public String createPost() {
        return "createPost";
    }

    @GetMapping("/{id}")
    public String findPostById() {
        return "findPostById";
    }

    @GetMapping
    public String findAllByPost() {
        return "findAllByPost";
    }

    @PutMapping("/{id}")
    public String updatePost() {
        return "updatePost";
    }

    @DeleteMapping("/{id}")
    public String deletePost() {
        return "deletePost";
    }
}
