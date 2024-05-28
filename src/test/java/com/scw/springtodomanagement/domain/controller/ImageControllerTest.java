package com.scw.springtodomanagement.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.springtodomanagement.domain.controller.image.ImageController;
import com.scw.springtodomanagement.domain.controller.image.ImageResponseDTO;
import com.scw.springtodomanagement.domain.service.ImageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ImageController.class)
@AutoConfigureMockMvc(addFilters = false)
class ImageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ImageService imageService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("이미지 업로드 - 성공")
    void uploadImage_Success() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "testImage.jpg",
                "image/jpeg",
                "some image data".getBytes()
        );
        String uuid = UUID.randomUUID().toString();
        ImageResponseDTO responseDTO = ImageResponseDTO.builder()
                .uploadFileName(file.getOriginalFilename())
                .UUIDFileName(uuid)
                .downloadURL("http://localhost:8080/api/v1/image/download/" + uuid)
                .build();

        when(imageService.save(any())).thenReturn(responseDTO);

        // when, then
        mockMvc.perform(multipart("/api/v1/image/upload")
                        .file(file))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("이미지 다운로드 - 성공")
//    void downloadImage_Success() throws Exception {
//        // given
//        String uuid = UUID.randomUUID().toString();
//        String filename = "testImage.jpg";
//        Resource resource = new ClassPathResource("image/" + filename);
//
//        when(imageService.downloadImage(uuid)).thenReturn(resource);
//
//        // when, then
//        String url = "/api/v1/image/download/" + uuid;
//        mockMvc.perform(get(url))
//                .andExpect(status().isOk());
//    }
//
//    @Test
//    @DisplayName("이미지 다운로드 - 실패 [찾고자 하는 이미지가 없음 ]")
//    void downloadImageNotFount() {
//        // given
//
//        // when
//
//        // then
//    }

}