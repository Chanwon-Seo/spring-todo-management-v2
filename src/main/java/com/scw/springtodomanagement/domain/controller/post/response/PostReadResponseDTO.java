package com.scw.springtodomanagement.domain.controller.post.response;

import com.scw.springtodomanagement.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class PostReadResponseDTO {
    private Long id;

    private String title;

    private String content;

    private String managerEmail;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public PostReadResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.managerEmail = post.getMember().getUserName();
        this.createdAt = post.getCreatedAt();
        this.lastModifiedAt = post.getLastModifiedAt();
    }

    public static PostReadResponseDTO of(Post post) {
        return PostReadResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .managerEmail(post.getMember().getUserName())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }

}
