package com.scw.springtodomanagement.domain.controller.post.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scw.springtodomanagement.domain.controller.attachedfile.response.AttachedFileCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.attachedfile.response.AttachedFileUpdateResponseDTO;
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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private AttachedFileUpdateResponseDTO attachedFileUpdateResponseDTO;

    public PostUpdateResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.managerEmail = post.getMember().getUserName();
        this.createdAt = post.getCreatedAt();
        this.lastModifiedAt = post.getLastModifiedAt();
    }

    public static PostUpdateResponseDTO of(final Post findPostData, AttachedFileUpdateResponseDTO attachedFileUpdateResponseDTO) {
        return PostUpdateResponseDTO.builder()
                .id(findPostData.getId())
                .title(findPostData.getTitle())
                .content(findPostData.getContent())
                .managerEmail(findPostData.getMember().getUserName())
                .createdAt(findPostData.getCreatedAt())
                .lastModifiedAt(findPostData.getLastModifiedAt())
                .attachedFileUpdateResponseDTO(attachedFileUpdateResponseDTO)
                .build();
    }
}
