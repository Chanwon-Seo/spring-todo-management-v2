package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.common.exception.errorcode.CommendErrorCode;
import com.scw.springtodomanagement.common.exception.errorcode.commend.CommendUnauthorizedException;
import com.scw.springtodomanagement.domain.controller.commend.request.CommendCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.commend.request.CommendUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.commend.response.CommendCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.commend.response.CommendReadResponseDTO;
import com.scw.springtodomanagement.domain.controller.commend.response.CommendUpdateResponseDTO;
import com.scw.springtodomanagement.domain.entity.Commend;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import com.scw.springtodomanagement.domain.entity.enums.CommendStatusType;
import com.scw.springtodomanagement.domain.repository.CommendRepository;
import com.scw.springtodomanagement.domain.repository.MemberRepository;
import com.scw.springtodomanagement.domain.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommendService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final CommendRepository commendRepository;

    @Transactional
    public CommendCreateResponseDTO createCommend(Long postId, CommendCreateRequestDTO requestDTO, String username) {
        Member findMember = memberRepository.findByUsernameOrElseThrow(username);
        Post findPost = postRepository.findByIdOrElseThrow(postId);

        Commend commendDomain = requestDTO.toCommendDomain(findMember, findPost);
        commendRepository.save(commendDomain);

        return CommendCreateResponseDTO.of(commendDomain);
    }

    @Transactional
    public CommendUpdateResponseDTO updateCommend(Long postId, Long commendId, CommendUpdateRequestDTO requestDTO, String username) {

        Member findMember = memberRepository.findByUsernameOrElseThrow(username);
        postRepository.findByIdOrElseThrow(postId);

        Commend findCommend = commendRepository.findByIdOrElseThrow(commendId);

        checkedCommendAuthorValidate(findCommend, findMember);
        findCommend.updateContents(requestDTO.content());

        return CommendUpdateResponseDTO.of(findCommend);
    }

    @Transactional
    public void deleteCommend(Long postId, Long commendId, String username) {
        Member findMember = memberRepository.findByUsernameOrElseThrow(username);
        postRepository.findByIdOrElseThrow(postId);

        Commend findCommend = commendRepository.findByIdOrElseThrow(commendId);

        checkedCommendAuthorValidate(findCommend, findMember);

        if (findCommend.getCommendStatsType().equals(CommendStatusType.DISABLE)) {
            throw new ApiException(CommendErrorCode.COMMEND_ALREADY_DELETED);
        }

        findCommend.updateCommendStatusType(CommendStatusType.DISABLE);
    }
    private static void checkedCommendAuthorValidate(Commend findCommend, Member findMember) {
        if (!findCommend.getMember().getId().equals(findMember.getId())) {
            throw new CommendUnauthorizedException();
        }
    }

}
