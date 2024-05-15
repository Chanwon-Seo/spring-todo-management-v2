package com.scw.springtodomanagement.domain.controller;


import com.scw.springtodomanagement.common.errorcode.StatusCode;
import com.scw.springtodomanagement.common.global.response.RestApiResponse;
import com.scw.springtodomanagement.domain.controller.request.ImageRequestDTO;
import com.scw.springtodomanagement.domain.controller.response.ImageResponseDTO;
import com.scw.springtodomanagement.domain.service.ImageService;
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

    @PostMapping("/upload")
    public ResponseEntity<RestApiResponse<ImageResponseDTO>> saveItem(
            @ModelAttribute ImageRequestDTO requestDTO) throws IOException {

        ImageResponseDTO responseDTO = imageService.uploadImage(requestDTO);

        return ResponseEntity.status(StatusCode.OK.code)
                .body(RestApiResponse.of("아래 링크에서 확인하세요", responseDTO));
    }

    @GetMapping("/download/{UUIDFilename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String UUIDFilename) throws IOException {
        Resource resource = imageService.downloadImage(UUIDFilename);

        return ResponseEntity.status(StatusCode.OK.code)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + UUIDFilename + "\"")
                .body(resource);
    }

}
