package com.scw.springtodomanagement.domain.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponseDTO {
    private String uploadFileName;
    private String UUIDFileName;

    public ImageResponseDTO(String uploadFileName, String UUIDFileName) {
        this.uploadFileName = uploadFileName;
        this.UUIDFileName = UUIDFileName;
    }
}
