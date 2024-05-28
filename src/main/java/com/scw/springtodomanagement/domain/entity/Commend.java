package com.scw.springtodomanagement.domain.entity;

import com.scw.springtodomanagement.domain.entity.enums.CommendStatusType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Commend extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commend_id")
    private Long id;

    private String contents;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Enumerated(EnumType.STRING)
    private CommendStatusType commendStatsType;

    @Builder
    public Commend(String contents, Member member, Post post, CommendStatusType commendStatsType) {
        this.contents = contents;
        this.member = member;
        this.post = post;
        this.commendStatsType = commendStatsType;
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }
    public void updateCommendStatusType(CommendStatusType commendStatsType) {
        this.commendStatsType = commendStatsType;
    }
}
