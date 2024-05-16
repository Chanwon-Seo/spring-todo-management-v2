package com.scw.springtodomanagement.domain.controller.response.image;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageResponseDTO {
    private String uploadFileName;
    private String UUIDFileName;
    private String downloadURL;

    public ImageResponseDTO(String uploadFileName, String UUIDFileName, String downloadURL) {
        this.uploadFileName = uploadFileName;
        this.UUIDFileName = UUIDFileName;
        this.downloadURL = downloadURL;
    }
}
