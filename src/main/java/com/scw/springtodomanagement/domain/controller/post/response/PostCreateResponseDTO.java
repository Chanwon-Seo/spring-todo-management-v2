package com.scw.springtodomanagement.domain.controller.post.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scw.springtodomanagement.domain.controller.attachedfile.response.AttachedFileCreateResponseDTO;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AttachedFileCreateResponseDTO attachedFileCreateResponseDTO;

    public PostCreateResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.managerEmail = post.getMember().getUserName();
        this.createdAt = post.getCreatedAt();
        this.lastModifiedAt = post.getLastModifiedAt();
    }

    public static PostCreateResponseDTO of(Post post, AttachedFileCreateResponseDTO attachedFileCreateResponseDTO) {
        return PostCreateResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .managerEmail(post.getMember().getUserName())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .attachedFileCreateResponseDTO(attachedFileCreateResponseDTO)
                .build();
    }

}
