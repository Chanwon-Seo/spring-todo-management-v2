package com.scw.springtodomanagement.domain.controller.commend;

import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import com.scw.springtodomanagement.common.global.response.RestApiResponse;
import com.scw.springtodomanagement.common.security.AuthenticationUser;
import com.scw.springtodomanagement.domain.controller.commend.request.CommendCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.commend.request.CommendUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.commend.response.CommendCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.commend.response.CommendUpdateResponseDTO;
import com.scw.springtodomanagement.domain.service.CommendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.scw.springtodomanagement.common.statuscode.StatusCode.CREATED;
import static com.scw.springtodomanagement.common.statuscode.StatusCode.OK;

@Tag(name = "05. Commend")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts/{postId}")
public class CommendController {

    private final CommendService commendService;

    @Operation(summary = "게시글 등록", description = "게시글을 등록하기 위한 Api\nschema에 있는 정보는 모두 필수 값이어야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "1. 필수 요청 정보가 비어있을 경우\n\n" +
                    "2. 존재하지 않는 게시물\n\n" +
                    "3. 요청 받을 내용을 미입력한 경우\n\n(응답 결과에 data는 없음)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/commends")
    public ResponseEntity<RestApiResponse<CommendCreateResponseDTO>> createPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal AuthenticationUser authenticationPrincipal,
            @Valid @RequestBody CommendCreateRequestDTO requestDTO
    ) {
        CommendCreateResponseDTO saveCommend = commendService.createCommend(postId, requestDTO, authenticationPrincipal.getUsername());
        return ResponseEntity.status(CREATED.code)
                .body(RestApiResponse.of(CREATED.code, saveCommend));
    }

    @Operation(summary = "댓글 수정", description = "댓글 수정을 위한 Api\nschema에 있는 정보는 모두 필수 값이어야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "1. 필수 요청 정보가 비어있을 경우\n\n" +
                    "2. 존재하지 않는 댓글\n\n" +
                    "3. 권한이 없는 유저 요청인 경우\n\n(응답 결과에 data는 없음)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PutMapping("/commends/{commendId}")
    public ResponseEntity<RestApiResponse<CommendUpdateResponseDTO>> updatePost(
            @PathVariable Long postId,
            @PathVariable Long commendId,
            @Valid @RequestBody CommendUpdateRequestDTO requestDTO,
            @AuthenticationPrincipal AuthenticationUser authenticationUser
    ) {
        CommendUpdateResponseDTO updateCommend = commendService.updateCommend(postId, commendId, requestDTO, authenticationUser.getUsername());

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of(updateCommend));
    }

    @Operation(summary = "댓글 삭제", description = "댓글 삭제를 위한 Api\n요청 파라미터는 필수 값이어야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "1. 등록 된 게시물이 아닌 경우\n\n" +
                    "2. 비밀번호 불일치\n\n(응답 결과에 data는 없음)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @DeleteMapping("/commends/{commendId}")
    public ResponseEntity<RestApiResponse<Object>> deletePost(
            @PathVariable Long postId,
            @PathVariable Long commendId,
            @AuthenticationPrincipal AuthenticationUser authenticationUser
    ) {
        commendService.deleteCommend(postId, commendId, authenticationUser.getUsername());

        return ResponseEntity.status(OK.code)
                .body(RestApiResponse.of("삭제되었습니다."));
    }
}
