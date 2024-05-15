package com.scw.springtodomanagement.controller;

import com.scw.springtodomanagement.common.global.response.RestApiResponse;
import com.scw.springtodomanagement.controller.request.PostCURequestDTO;
import com.scw.springtodomanagement.controller.request.PostDRequestDTO;
import com.scw.springtodomanagement.controller.response.PostResponseDTO;
import com.scw.springtodomanagement.service.PostService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.scw.springtodomanagement.common.errorcode.StatusCode.*;

@Tag(name = "02. Post")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<RestApiResponse<PostResponseDTO>> createPost(
            @Valid @RequestBody PostCURequestDTO requestDTO
    ) {
        PostResponseDTO savePost = postService.createPost(requestDTO);

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(savePost));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestApiResponse<PostResponseDTO>> findPostById(
            @PathVariable Long id
    ) {
        PostResponseDTO findPost = postService.findPostById(id);

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(findPost));
    }

    @GetMapping
    public ResponseEntity<RestApiResponse<List<PostResponseDTO>>> findAll() {
        List<PostResponseDTO> findAllPost = postService.findAll();
        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(findAllPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestApiResponse<PostResponseDTO>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostCURequestDTO requestDTO
    ) {
        PostResponseDTO updatePost = postService.updatePost(id, requestDTO);
        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(updatePost));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse<Object>> deletePost(
            @PathVariable Long id,
            @RequestBody PostDRequestDTO requestDTO
    ) {
        postService.deletePost(id, requestDTO);

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of("삭제되엇습니다."));
    }
}
