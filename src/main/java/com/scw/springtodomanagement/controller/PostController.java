package com.scw.springtodomanagement.controller;

import com.scw.springtodomanagement.controller.request.PostCURequestDTO;
import com.scw.springtodomanagement.controller.response.PostResponseDTO;
import com.scw.springtodomanagement.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "02. Post")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponseDTO createPost(
            @RequestBody PostCURequestDTO requestDTO
    ) {
        return postService.createPost(requestDTO);
    }

    @GetMapping("/{id}")
    public PostResponseDTO findPostById(
            @PathVariable Long id
    ) {
        return postService.findPostById(id);
    }

    @GetMapping
    public List<PostResponseDTO> findAll() {
        return postService.findAll();
    }

    @PutMapping("/{id}")
    public PostResponseDTO updatePost(
            @PathVariable Long id,
            @RequestBody PostCURequestDTO requestDTO
    ) {
        return postService.updatePost(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deletePost(
            @PathVariable Long id,
            @RequestBody Map<String, String> requestPassword
    ) {
        postService.deletePost(id, requestPassword);

        return "삭제되었습니다.";
    }
}
