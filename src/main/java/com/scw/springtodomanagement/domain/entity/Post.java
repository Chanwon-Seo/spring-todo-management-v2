package com.scw.springtodomanagement.domain.entity;

import com.scw.springtodomanagement.domain.controller.post.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.entity.enums.MemberRoleType;
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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PostStateType postStateType;

    @Builder
    public Post(String title, String content, Member member, PostStateType postStateType) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.postStateType = postStateType;
    }

    public void updateTitle(PostUpdateRequestDTO requestDTO) {
        this.title = requestDTO.getTitle();
        this.content = requestDTO.getContent();
    }

    public void deleteTitle(PostStateType postStateType) {
        this.postStateType = postStateType;
    }
}
