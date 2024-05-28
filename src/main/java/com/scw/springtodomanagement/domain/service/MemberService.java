package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.domain.controller.member.request.MemberSignupRequestDTO;
import com.scw.springtodomanagement.domain.controller.member.response.MemberSignupResponseDTO;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.enums.MemberRoleType;
import com.scw.springtodomanagement.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public MemberSignupResponseDTO signup(final MemberSignupRequestDTO requestDTO) {
        memberRepository.checkDuplicateUsername(requestDTO.username());

        Member member = Member.builder()
                .nickName(requestDTO.nickname())
                .userName(requestDTO.username())
                .password(NoOpPasswordEncoder.getInstance().encode(requestDTO.password()))
                .memberRoleType(MemberRoleType.USER)
                .build();

        memberRepository.save(member);

        return MemberSignupResponseDTO.of(member);
    }

}
