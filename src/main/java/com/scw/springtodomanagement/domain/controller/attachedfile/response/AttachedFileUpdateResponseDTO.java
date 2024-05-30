package com.scw.springtodomanagement.domain.controller.attachedfile.response;

import com.scw.springtodomanagement.domain.entity.AttachedFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AttachedFileUpdateResponseDTO {

    private Long attachedFileId;
    private String attachedFileUrl;
    private String attachedOriginalFilename;
    private String attachedUUIDFilename;
    private String imageExtensionType;

    public static AttachedFileUpdateResponseDTO of(AttachedFile attachedFile) {
        return AttachedFileUpdateResponseDTO.builder()
                .attachedFileId(attachedFile.getId())
                .attachedFileUrl("http://localhost:8080/api/v1/attachedfile/" + attachedFile.getUUIDFilename())
                .attachedOriginalFilename(attachedFile.getOriginalFilename())
                .attachedUUIDFilename(attachedFile.getUUIDFilename())
                .imageExtensionType(attachedFile.getImageExtensionType().getFileExtension())
                .build();
    }
}
