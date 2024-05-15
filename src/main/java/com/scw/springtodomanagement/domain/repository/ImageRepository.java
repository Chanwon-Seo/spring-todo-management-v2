package com.scw.springtodomanagement.domain.repository;

import com.scw.springtodomanagement.common.errorcode.ImageErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByExtractFilename(String name);

    default Image findByExtractFilenameOrElseThrow(String name) {
        return findByExtractFilename(name)
                .orElseThrow(() -> new ApiException(ImageErrorCode.NOT_FOUND_IMAGE_FILE));
    }


}
