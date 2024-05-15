package com.scw.springtodomanagement.domain.repository;

import com.scw.springtodomanagement.common.errorcode.PostErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new ApiException(PostErrorCode.NOT_FOUND_POST));
    }
}
