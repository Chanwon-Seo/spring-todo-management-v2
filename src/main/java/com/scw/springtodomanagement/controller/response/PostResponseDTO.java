package com.scw.springtodomanagement.controller.response;

import com.scw.springtodomanagement.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostResponseDTO {
    private Long id;

    private String title;

    private String content;

    private String manager;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.manager = post.getManagerEmail();
        this.createdAt = post.getCreatedAt();
        this.lastModifiedAt = post.getLastModifiedAt();
    }

}
