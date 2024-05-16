package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.errorcode.PostErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.controller.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.request.PostDeleteRequestDTO;
import com.scw.springtodomanagement.domain.controller.response.post.PostCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.response.post.PostReadResponseDTO;
import com.scw.springtodomanagement.domain.controller.response.post.PostUpdateResponseDTO;
import com.scw.springtodomanagement.domain.entity.enums.DomainType;
import com.scw.springtodomanagement.domain.repository.PostRepository;
import com.scw.springtodomanagement.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    /**
     * create
     * throw 도메인 검증 { google, naver, github}
     */
    @Transactional
    public PostCreateResponseDTO createPost(PostCreateRequestDTO requestDTO) {
        DomainType.fromDomainValidation(requestDTO.getManagerEmail());
        Post savePost = postRepository.save(requestDTO.toPostDomain());

        return PostCreateResponseDTO.builder()
                .id(savePost.getId())
                .title(savePost.getTitle())
                .content(savePost.getContent())
                .managerEmail(savePost.getManagerEmail())
                .createdAt(savePost.getCreatedAt())
                .lastModifiedAt(savePost.getLastModifiedAt())
                .build();
    }

    /**
     * 단건 조회
     * throw 게시물 여부 검증
     */
    public PostReadResponseDTO findPostById(Long id) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);
        return PostReadResponseDTO.builder()
                .id(findPostData.getId())
                .title(findPostData.getTitle())
                .content(findPostData.getContent())
                .managerEmail(findPostData.getManagerEmail())
                .createdAt(findPostData.getCreatedAt())
                .lastModifiedAt(findPostData.getLastModifiedAt())
                .build();
    }

    /**
     * 다건 조회
     * 작성일 기준 내림차순
     */
    public List<PostReadResponseDTO> findAll() {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt")).stream()
                .map(PostReadResponseDTO::new)
                .toList();
    }

    /**
     * update
     * throw 게시물 여부 검증
     * throw 도메인 검증 { google, naver, github}
     */
    @Transactional
    public PostUpdateResponseDTO updatePost(Long id, PostUpdateRequestDTO requestDTO) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);
        DomainType.fromDomainValidation(requestDTO.getManagerEmail());

        passwordValidation(requestDTO.getPassword(), findPostData.getPassword());

        findPostData.updateTitle(requestDTO);
        return PostUpdateResponseDTO.builder()
                .id(findPostData.getId())
                .title(findPostData.getTitle())
                .content(findPostData.getContent())
                .managerEmail(findPostData.getManagerEmail())
                .createdAt(findPostData.getCreatedAt())
                .lastModifiedAt(findPostData.getLastModifiedAt())
                .build();
    }

    /**
     * delete
     * throw 게시글 여부 검증
     * throw 비밀번호 동일 여부 검증
     */
    @Transactional
    public void deletePost(Long id, PostDeleteRequestDTO requestDTO) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);

        passwordValidation(requestDTO.getPassword(), findPostData.getPassword());

        postRepository.delete(findPostData);
    }

    /**
     * 비밀번호 검증
     */
    private void passwordValidation(String inputPassword, String findPassword) {
        if (!inputPassword.equals(findPassword)) {
            throw new ApiException(PostErrorCode.PASSWORD_VERIFY_FAIL);
        }
    }
}
