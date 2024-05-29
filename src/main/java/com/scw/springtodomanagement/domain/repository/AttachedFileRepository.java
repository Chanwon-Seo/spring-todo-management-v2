package com.scw.springtodomanagement.domain.repository;

import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.common.exception.errorcode.AttachedFileErrorCode;
import com.scw.springtodomanagement.domain.entity.AttachedFile;
import com.scw.springtodomanagement.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface AttachedFileRepository extends JpaRepository<AttachedFile, Long> {

    Optional<AttachedFile> findByPost(Post post);

    default AttachedFile findByAttachedFileOrElseThrow(Post post) {
        return findByPost(post).orElseThrow(() ->
                new ApiException(AttachedFileErrorCode.NOT_FOUND_COMMEND));
    }
}
