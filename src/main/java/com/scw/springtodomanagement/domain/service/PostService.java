package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.errorcode.PostErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.dto.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.dto.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.dto.request.PostDeleteRequestDTO;
import com.scw.springtodomanagement.domain.dto.response.post.PostCreateResponseDTO;
import com.scw.springtodomanagement.domain.dto.response.post.PostReadResponseDTO;
import com.scw.springtodomanagement.domain.dto.response.post.PostUpdateResponseDTO;
import com.scw.springtodomanagement.domain.entity.enums.DomainType;
import com.scw.springtodomanagement.domain.entity.enums.StateType;
import com.scw.springtodomanagement.domain.repository.PostRepository;
import com.scw.springtodomanagement.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.scw.springtodomanagement.common.errorcode.PostErrorCode.POST_ALREADY_DELETED;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    /**
     * create
     * throw 도메인 검증 { google, naver, github }
     */
    @Transactional
    public PostCreateResponseDTO createPost(final PostCreateRequestDTO requestDTO) {
        DomainType.fromDomainValidation(requestDTO.managerEmail());
        Post savePost = postRepository.save(requestDTO.toPostDomain());

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
        return postRepository.findAllByStateTypeOrderByCreatedAtDesc(StateType.ENABLE).stream()
                .map(PostReadResponseDTO::new)
                .toList();
    }

    /**
     * update
     * throw 게시물 여부 검증능
     * throw 도메인 검증 { google, naver, github}
     */
    @Transactional
    public PostUpdateResponseDTO updatePost(final Long id, final PostUpdateRequestDTO requestDTO) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);
        DomainType.fromDomainValidation(requestDTO.managerEmail());

        passwordValidation(requestDTO.password(), findPostData.getPassword());

        findPostData.updateTitle(requestDTO);

        return PostUpdateResponseDTO.of(findPostData);
    }

    /**
     * delete
     * throw 게시글 여부 검증
     * throw 비밀번호 동일 여부 검증
     */
    @Transactional
    public void deletePost(final Long id, final PostDeleteRequestDTO requestDTO) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);

        if (findPostData.getStateType().equals(StateType.DISABLE)) {
            throw new ApiException(POST_ALREADY_DELETED);
        }

        passwordValidation(requestDTO.password(), findPostData.getPassword());

        findPostData.deleteTitle(StateType.DISABLE);
    }

    /**
     * 비밀번호 검증
     */
    private void passwordValidation(final String inputPassword, final String findPassword) {
        if (!inputPassword.equals(findPassword)) {
            throw new ApiException(PostErrorCode.PASSWORD_VERIFY_FAIL);
        }
    }
}
