package com.scw.springtodomanagement.domain.controller;


import com.scw.springtodomanagement.common.errorcode.StatusCode;
import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import com.scw.springtodomanagement.common.global.response.RestApiResponse;
import com.scw.springtodomanagement.domain.controller.request.ImageRequestDTO;
import com.scw.springtodomanagement.domain.controller.response.image.ImageResponseDTO;
import com.scw.springtodomanagement.domain.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "03. Image")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "이미지 업로드", description = "이미지 업로드 Api\nschema에 있는 정보는 모두 필수 값이어야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "1. 지원하지 않는 확장자\n\n2. 요청한 이미지가 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/upload")
    public ResponseEntity<RestApiResponse<ImageResponseDTO>> saveItem(
            @ModelAttribute ImageRequestDTO requestDTO) throws IOException {

        ImageResponseDTO responseDTO = imageService.uploadImage(requestDTO);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("아래 링크에서 확인하세요", responseDTO));
    }

    @Operation(summary = "이미지 다운로드", description = "이미지 다운로드 Api\n요청 파라미터는 필수 값이어야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "1. 등록된 이미지 정보가 아닌 경우",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @GetMapping("/download/{UUIDFilename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String UUIDFilename) throws IOException {
        Resource resource = imageService.downloadImage(UUIDFilename);

        return ResponseEntity.status(StatusCode.OK.code)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UUIDFilename + "\"")
                .body(resource);
    }

}
