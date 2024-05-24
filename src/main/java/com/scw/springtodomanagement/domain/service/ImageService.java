package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.errorcode.ImageErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.dto.request.ImageRequestDTO;
import com.scw.springtodomanagement.domain.dto.response.image.ImageResponseDTO;
import com.scw.springtodomanagement.domain.entity.Image;
import com.scw.springtodomanagement.domain.entity.enums.ImageExtensionType;
import com.scw.springtodomanagement.domain.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional
    public ImageResponseDTO uploadImage(ImageRequestDTO requestDTO) throws IOException {
        MultipartFile imageFile = requestDTO.getImageFile();

        if (imageFile.getOriginalFilename().isBlank()) {
            throw new ApiException(ImageErrorCode.UNSUPPORTED_IMAGE_EXTENSION);
        }

        String originalFilename = imageFile.getOriginalFilename();
        String extractExtension = extractExt(imageFile.getOriginalFilename().toLowerCase());

        ImageExtensionType.fromFileExtension(extractExtension);

        String uuidFilename = createFilename();
        requestDTO.getImageFile().transferTo(new File(getFullPath(uuidFilename + "." + extractExtension)));

        Image newImageData = Image.builder()
                .originalFilename(originalFilename)
                .extractFilename(uuidFilename)
                .build();

        imageRepository.save(newImageData);

        return ImageResponseDTO.builder()
                .uploadFileName(originalFilename)
                .UUIDFileName(uuidFilename)
                .downloadURL("http://localhost:8080/api/v1/image/download/" + uuidFilename)
                .build();
    }

    public Resource downloadImage(String UUIDFilename) throws IOException {
        Image findImage = imageRepository.findByExtractFilenameOrElseThrow(UUIDFilename);
        String extractExtension = extractExt(findImage.getOriginalFilename());

        findImage.updateExtractFilename(findImage.getExtractFilename() + "." + extractExtension);

        Path filePath = Paths.get(getFullPath(findImage.getExtractFilename()));

        return new InputStreamResource(Files.newInputStream(filePath));
    }

    public String findByExtractFilename(String UUIDFilename) {
        Image findImage = imageRepository.findByExtractFilenameOrElseThrow(UUIDFilename);
        return extractExt(findImage.getOriginalFilename());
    }

    public String getFullPath(String filename) {
        return System.getProperty("user.dir") + "/src/main/resources/image/" + filename;
    }

    private String createFilename() {
        return UUID.randomUUID().toString();
    }

    private String extractExt(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }


}
