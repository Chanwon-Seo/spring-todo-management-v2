package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.errorcode.ImageErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.controller.request.ImageRequestDTO;
import com.scw.springtodomanagement.domain.controller.response.image.ImageResponseDTO;
import com.scw.springtodomanagement.domain.repository.ImageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ImageServiceTest {

    @Mock
    private ImageRepository imageRepository;

    @InjectMocks
    private ImageService imageService;

    @Autowired
    private ResourceLoader resourceLoader;


    @Test
    @DisplayName("이미지 저장 - 성공")
    void uploadImage_Success() throws IOException {
        // given
        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "testImage.jpg",
                "image/jpeg",
                "some image data".getBytes()
        );

        ImageRequestDTO requestDTO = ImageRequestDTO.builder()
                .imageFile(file)
                .build();

        // when
        ImageResponseDTO imageResponseDTO = imageService.uploadImage(requestDTO);

        // then
        String fullPath = imageService.getFullPath(imageResponseDTO.getUUIDFileName());
        Resource resource = resourceLoader.getResource("classpath:image/" + fullPath);
        Assertions.assertEquals(resource.getFilename(), imageResponseDTO.getUUIDFileName());
    }

//    @Test
//    @DisplayName("이미지 저장 및 다운로드 - 성공")
//    void uploadAndDownloadImage_Success() throws IOException {
//        // given
//        MockMultipartFile file = new MockMultipartFile(
//                "imageFile", // 파라미터 이름
//                "testImage.jpg", // 파일 이름
//                "image/jpeg", // 컨텐츠 타입
//                "asdfasdf".getBytes() // 파일 데이터
//        );
//
//        ImageRequestDTO requestDTO = ImageRequestDTO.builder()
//                .imageFile(file)
//                .build();
//
//        Image saveImage = Image.builder()
//                .extractFilename("randomUUIDFileName")
//                .build();
//
//        when(imageRepository.save(any(Image.class))).thenReturn(saveImage);
//
//        // when
//        ImageResponseDTO imageResponseDTO = imageService.uploadImage(requestDTO);
//
//        // then
//        String fullPath = imageService.getFullPath(imageResponseDTO.getUUIDFileName());
//        Resource resource = resourceLoader.getResource("classpath:image/" + fullPath);
//
//        // 이미지 다운로드
//        Resource downloadResource = imageService.downloadImage(imageResponseDTO.getUUIDFileName());
//
//        // 이미지 다운로드 후 원본 이미지 데이터와 일치하는지 확인
//        Assertions.assertEquals(resource.getFilename(), downloadResource.getFilename(), "이미지가 정확히 다운로드되었는지 확인합니다.");
//    }

    @Test
    @DisplayName("파일 업로드 - 실패 [ 지원하지 않는 파일 확장자]")
    void uploadImageExtensionUnSupported() {

        MockMultipartFile file = new MockMultipartFile(
                "imageFile", // 파라미터 이름
                "testImage.txt", // 파일 이름
                "image/txt", // 컨텐츠 타입
                "some image data".getBytes() // 파일 데이터
        );

        ImageRequestDTO requestDTO = ImageRequestDTO.builder()
                .imageFile(file)
                .build();

        // when
        ApiException exception = assertThrows(ApiException.class, () ->
                imageService.uploadImage(requestDTO)
        );

        Assertions.assertEquals(
                ImageErrorCode.UNSUPPORTED_IMAGE_EXTENSION.getDescription(),
                exception.getMessage()
        );
    }
}