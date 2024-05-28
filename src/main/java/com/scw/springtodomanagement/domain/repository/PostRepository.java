package com.scw.springtodomanagement.domain.repository;

import com.scw.springtodomanagement.common.exception.errorcode.PostErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.entity.Post;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByPostStateTypeOrderByCreatedAtDesc(PostStateType postStateType);


    default Post findByIdOrElseThrow(Long id) {
        Post post = findById(id).orElseThrow(() ->
                new ApiException(PostErrorCode.NOT_FOUND_POST));

        if (post.getPostStateType().equals(PostStateType.DISABLE)) {
            throw new ApiException(PostErrorCode.POST_ALREADY_DELETED);
        }

        return post;
    }

}
