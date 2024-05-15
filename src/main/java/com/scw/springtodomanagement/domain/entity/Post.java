package com.scw.springtodomanagement.domain.entity;

import com.scw.springtodomanagement.domain.controller.request.PostCURequestDTO;
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

    @Builder
    public Post(String title, String content, String managerEmail, String password) {
        this.title = title;
        this.content = content;
        this.managerEmail = managerEmail;
        this.password = password;
    }

    public void updateTitle(PostCURequestDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
        this.managerEmail = requestDTO.getManagerEmail();
    }
}
