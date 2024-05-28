package com.scw.springtodomanagement.domain.controller.member.response;

import com.scw.springtodomanagement.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSignupResponseDTO {
    private String nickname;
    private String username;

    public static MemberSignupResponseDTO of(Member member) {
        return MemberSignupResponseDTO.builder()
                .nickname(member.getNickName())
                .username(member.getUserName())
                .build();
    }
}
