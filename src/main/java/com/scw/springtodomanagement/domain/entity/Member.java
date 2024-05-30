package com.scw.springtodomanagement.domain.entity;

import com.scw.springtodomanagement.domain.entity.enums.MemberRoleType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "createdAt", column = @Column(name = "signupAt"))
@AllArgsConstructor
public class Member extends BaseTimeCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String nickName;

    /**
     * 이메일
     */
    @Column(name = "email", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private MemberRoleType memberRoleType;

    @Builder
    public Member(String nickName, String userName, String password, MemberRoleType memberRoleType) {
        this.nickName = nickName;
        this.userName = userName;
        this.password = password;
        this.memberRoleType = memberRoleType;
    }
}
