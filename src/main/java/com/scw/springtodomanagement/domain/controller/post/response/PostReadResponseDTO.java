package com.scw.springtodomanagement.domain.controller.post.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.scw.springtodomanagement.domain.controller.commend.response.CommendReadResponseDTO;
import com.scw.springtodomanagement.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class PostReadResponseDTO {
    private Long id;
    private String title;
    private String content;
    private String managerEmail;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String attachedFileUrl;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<CommendReadResponseDTO> commendReadResponseDTOList;

    public PostReadResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.managerEmail = post.getMember().getUserName();
        this.createdAt = post.getCreatedAt();
        this.lastModifiedAt = post.getLastModifiedAt();
        this.commendReadResponseDTOList = post.getCommendList().stream()
                .map(CommendReadResponseDTO::new)
                .toList();
    }

    public static PostReadResponseDTO of(Post post) {
        String url = null;
        if (post.getAttachedFile() != null) {
            url = "http://localhost:8080/api/v1/attachedfile/" +
                    post.getAttachedFile().getUUIDFilename();
        }
        return PostReadResponseDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .managerEmail(post.getMember().getUserName())
                .attachedFileUrl(url)
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .commendReadResponseDTOList(post.getCommendList().stream()
                        .map(CommendReadResponseDTO::new)
                        .toList())
                .build();
    }

    public static List<PostReadResponseDTO> of(List<Post> postList) {
        return postList.stream()
                .map(PostReadResponseDTO::of)
                .toList();
    }
}