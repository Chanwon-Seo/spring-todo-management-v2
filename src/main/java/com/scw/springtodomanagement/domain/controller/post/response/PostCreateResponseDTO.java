package com.scw.springtodomanagement.domain.controller.post.response;

import com.scw.springtodomanagement.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostCreateResponseDTO {
    private Long id;

    private String title;

    private String content;

    private String managerEmail;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public PostCreateResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.managerEmail = post.getManagerEmail();
        this.createdAt = post.getCreatedAt();
        this.lastModifiedAt = post.getLastModifiedAt();
    }

}
