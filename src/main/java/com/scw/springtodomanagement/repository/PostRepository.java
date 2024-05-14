package com.scw.springtodomanagement.repository;

import com.scw.springtodomanagement.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
