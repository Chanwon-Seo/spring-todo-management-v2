package com.scw.springtodomanagement.domain.entity.enums;

import com.scw.springtodomanagement.common.exception.errorcode.ImageErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ImageExtensionType {
    JPEG("jpeg"),
    JPG("jpg"),
    PNG("png"),
    ;

    private final String fileExtension;

    public static void fromFileExtension(String fileExtension) {
        for (ImageExtensionType value : values()) {
            if (fileExtension.equals(value.getFileExtension())) {
                return;
            }
        }
        throw new ApiException(ImageErrorCode.UNSUPPORTED_IMAGE_EXTENSION);
    }
}
