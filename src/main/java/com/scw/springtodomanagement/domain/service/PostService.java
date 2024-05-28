package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.exception.post.PostUnauthorizedException;
import com.scw.springtodomanagement.domain.controller.post.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostReadResponseDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostUpdateResponseDTO;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import com.scw.springtodomanagement.domain.repository.MemberRepository;
import com.scw.springtodomanagement.domain.repository.PostRepository;
import com.scw.springtodomanagement.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    /**
     * create
     * throw 도메인 검증 { google, naver, github }
     */
    @Transactional
    public PostCreateResponseDTO createPost(final PostCreateRequestDTO requestDTO, final String authUsername) {

        Member findMember = memberRepository.findByUsernameOrElseThrow(authUsername);

        Post savePost = postRepository.save(requestDTO.toPostDomain(findMember));

        return PostCreateResponseDTO.of(savePost);
    }

    /**
     * 단건 조회
     * throw 게시물 여부 검증
     */
    public PostReadResponseDTO findPostById(final Long id) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);

        return PostReadResponseDTO.of(findPostData);
    }

    /**
     * 다건 조회
     * 작성일 기준 내림차순
     */
    public List<PostReadResponseDTO> findAll() {
        return postRepository.findAllByPostStateTypeOrderByCreatedAtDesc(PostStateType.ENABLE).stream()
                .map(PostReadResponseDTO::new)
                .toList();
    }

    /**
     * update
     * throw 게시물 여부 검증
     * throw 도메인 검증 { google, naver, github}
     */
    @Transactional
    public PostUpdateResponseDTO updatePost(final Long postId, final PostUpdateRequestDTO requestDTO, final String authUsername) {
        Member findMember = memberRepository.findByUsernameOrElseThrow(authUsername);
        Post findPostData = postRepository.findByIdOrElseThrow(postId);

        checkedUserPostAuthorValidate(findPostData.getId(), findMember.getId());

        findPostData.updateTitle(requestDTO);

        return PostUpdateResponseDTO.of(findPostData);
    }


    /**
     * delete
     * throw 게시글 여부 검증
     * throw 비밀번호 동일 여부 검증
     */
    @Transactional
    public void deletePost(final Long id, String username) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);
        Member findMemberData = memberRepository.findByUsernameOrElseThrow(username);
        checkedUserPostAuthorValidate(findPostData.getId(), findMemberData.getId());

        findPostData.deleteTitle(PostStateType.DISABLE);
    }

    private void checkedUserPostAuthorValidate(Long postId, Long userId) {
        if (!Objects.equals(postId, userId)) {
            throw new PostUnauthorizedException();
        }
    }
}
