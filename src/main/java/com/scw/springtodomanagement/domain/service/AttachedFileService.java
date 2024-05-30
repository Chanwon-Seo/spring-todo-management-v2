package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.common.exception.errorcode.AttachedFileErrorCode;
import com.scw.springtodomanagement.domain.controller.attachedfile.response.AttachedFileCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.attachedfile.response.AttachedFileUpdateResponseDTO;
import com.scw.springtodomanagement.domain.entity.AttachedFile;
import com.scw.springtodomanagement.domain.entity.Post;
import com.scw.springtodomanagement.domain.entity.enums.AttacheFileStatueType;
import com.scw.springtodomanagement.domain.entity.enums.ImageExtensionType;
import com.scw.springtodomanagement.domain.repository.AttachedFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachedFileService {

    public final AttachedFileRepository attachedFileRepository;

    public AttachedFileCreateResponseDTO saveAttachedFile(MultipartFile multipartFile, Post post) {
        AttachedFile AttachedFileDomain = validateAttachedFile(multipartFile, post);

        AttachedFile saveAttacheFile = attachedFileRepository.save(AttachedFileDomain);
        return AttachedFileCreateResponseDTO.of(saveAttacheFile);
    }

    @Transactional
    public AttachedFileUpdateResponseDTO updateAttacheFile(MultipartFile multipartFile, Post post) {
        Optional<AttachedFile> findAttachedFileData = attachedFileRepository.findByPost(post);


        //multipartFile이 존재한다면
        if (multipartFile != null) {
            //findAttachedFileData이 존재한다면 update
            if (!findAttachedFileData.isEmpty()) {
                AttachedFile updateAttachedFileData = validateAttachedFile(multipartFile, post);
                findAttachedFileData.get().updateAttachedFile(updateAttachedFileData);

            } else {
                //findAttachedFileData이 존재하지 않는다면 save
                saveAttachedFile(multipartFile, post);
            }
            return AttachedFileUpdateResponseDTO.of(findAttachedFileData.get());
        } else {
            findAttachedFileData.ifPresent(attachedFile -> attachedFile.deleteAttachedFile(AttacheFileStatueType.DISABLE));
            return null;
        }
    }

    public Optional<AttachedFile> findByAttachedFile(Post findPostData) {
        return attachedFileRepository.findByPost(findPostData);

    }

    public AttachedFile validateAttachedFile(MultipartFile imageFile, Post post) {
        String originalFilename = imageFile.getOriginalFilename();
        String onlyFilename = validateImageFileName(originalFilename);
        ImageExtensionType imageExtensionType = validateImageExtension(originalFilename);
        String uuidFilename = createUUIDFilename();

        try {
            return AttachedFile.builder()
                    .originalFilename(onlyFilename)
                    .UUIDFilename(uuidFilename)
                    .imageExtensionType(imageExtensionType)
                    .fileSize(imageFile.getBytes().length)
                    .attachedContent(imageFile.getBytes())
                    .post(post)
                    .attacheFileStatueType(AttacheFileStatueType.ENABLE)
                    .build();
        } catch (IOException e) {
            throw new ApiException(AttachedFileErrorCode.IMAGE_PROCESSING_ERROR);
        }
    }

    //확장자 검증
    private ImageExtensionType validateImageExtension(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        return ImageExtensionType.fromFileExtension(extension);
    }

    //파일명 검증
    private String validateImageFileName(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        String result = originalFilename.substring(0, pos);
        if (result.isBlank()) {
            throw new ApiException(AttachedFileErrorCode.INVALID_FILENAME);
        }
        return result;
    }

    private String createUUIDFilename() {
        return UUID.randomUUID().toString();
    }

}
