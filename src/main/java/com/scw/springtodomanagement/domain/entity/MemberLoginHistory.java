package com.scw.springtodomanagement.domain.entity;

import com.scw.springtodomanagement.domain.entity.enums.MemberLoginHistoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "createdAt", column = @Column(name = "loginAt"))
@AllArgsConstructor
public class MemberLoginHistory extends BaseTimeCreateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberLoginHistoryStatus memberLoginHistoryStatus;

    @Builder
    public MemberLoginHistory(Member member, String username, MemberLoginHistoryStatus memberLoginHistoryStatus) {
        this.member = member;
        this.username = username;
        this.memberLoginHistoryStatus = memberLoginHistoryStatus;
    }
}
