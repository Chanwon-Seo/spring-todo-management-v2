package com.scw.springtodomanagement.domain.controller.response.post;

import com.scw.springtodomanagement.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostUpdateResponseDTO {
    private Long id;

    private String title;

    private String content;

    private String managerEmail;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public PostUpdateResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.managerEmail = post.getManagerEmail();
        this.createdAt = post.getCreatedAt();
        this.lastModifiedAt = post.getLastModifiedAt();
    }

}
