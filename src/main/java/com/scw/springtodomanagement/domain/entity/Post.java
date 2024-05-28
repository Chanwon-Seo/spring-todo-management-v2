package com.scw.springtodomanagement.domain.entity;

import com.scw.springtodomanagement.domain.controller.post.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String managerEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private PostStateType postStateType;

    @Builder
    public Post(String title, String content, String managerEmail, String password, PostStateType postStateType) {
        this.title = title;
        this.content = content;
        this.managerEmail = managerEmail;
        this.password = password;
        this.postStateType = postStateType;
    }

    public void updateTitle(PostUpdateRequestDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.managerEmail = requestDTO.getManagerEmail();
    }

    public void deleteTitle(PostStateType postStateType) {
        this.postStateType = postStateType;
    }
}
