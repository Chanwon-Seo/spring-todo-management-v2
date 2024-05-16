package com.scw.springtodomanagement.domain.controller;

import com.scw.springtodomanagement.common.global.response.RestApiResponse;
import com.scw.springtodomanagement.domain.controller.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.request.PostDeleteRequestDTO;
import com.scw.springtodomanagement.domain.controller.response.post.PostCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.response.post.PostReadResponseDTO;
import com.scw.springtodomanagement.domain.controller.response.post.PostUpdateResponseDTO;
import com.scw.springtodomanagement.domain.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "게시글 등록", description = "게시글을 등록하기 위한 Api\nschema에 있는 정보는 모두 필수 값이어야 합니다.")
    @PostMapping
    public ResponseEntity<RestApiResponse<PostCreateResponseDTO>> createPost(
            @Valid @RequestBody PostCreateRequestDTO requestDTO
    ) {
        PostCreateResponseDTO savePost = postService.createPost(requestDTO);

        return ResponseEntity.status(CREATED.code)
                .body(RestApiResponse.of(CREATED.code, savePost));
    }

    @Operation(summary = "게시글 단건 조회", description = "게시글 단건 조회를 위한 Api\n요청 파라미터는 필수 값이어야 합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<RestApiResponse<PostReadResponseDTO>> findPostById(
            @PathVariable Long id
    ) {
        PostReadResponseDTO findPost = postService.findPostById(id);

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(findPost));
    }

    @Operation(summary = "게시글 다건 조회", description = "게시글 다건 조회를 위한 Api\n별도의 요청 파라미터는 없습니다. 작성일 기준으로 내림차순 됩니다.")
    @GetMapping
    public ResponseEntity<RestApiResponse<List<PostReadResponseDTO>>> findAll() {
        List<PostReadResponseDTO> findAllPost = postService.findAll();

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(findAllPost));
    }

    @Operation(summary = "게시글 수정", description = "게시글 수정을 위한 Api\nschema에 있는 정보는 모두 필수 값이어야 합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<RestApiResponse<PostUpdateResponseDTO>> updatePost(
            @PathVariable Long id,
            @Valid @RequestBody PostUpdateRequestDTO requestDTO
    ) {
        PostUpdateResponseDTO updatePost = postService.updatePost(id, requestDTO);
        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(updatePost));
    }

    @Operation(summary = "게시글 삭제", description = "게시글 삭제를 위한 Api\n요청 파라미터는 필수 값이어야 합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<RestApiResponse<Object>> deletePost(
            @PathVariable Long id,
            @RequestBody PostDeleteRequestDTO requestDTO
    ) {
        postService.deletePost(id, requestDTO);

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of("삭제되엇습니다."));
    }
}
