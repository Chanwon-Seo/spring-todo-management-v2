package com.scw.springtodomanagement.repository;

import com.scw.springtodomanagement.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시물입니다."));
    }
}
