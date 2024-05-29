package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.common.exception.post.PostUnauthorizedException;
import com.scw.springtodomanagement.domain.controller.attachedfile.response.AttachedFileCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.attachedfile.response.AttachedFileUpdateResponseDTO;
import com.scw.springtodomanagement.domain.controller.commend.response.CommendReadResponseDTO;
import com.scw.springtodomanagement.domain.controller.post.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostReadResponseDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostUpdateResponseDTO;
import com.scw.springtodomanagement.domain.entity.AttachedFile;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.enums.AttacheFileStatueType;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import com.scw.springtodomanagement.domain.repository.MemberRepository;
import com.scw.springtodomanagement.domain.repository.PostRepository;
import com.scw.springtodomanagement.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final AttachedFileService attachedFileService;
    private final CommendService commendService;

    /**
     * create
     * throw 도메인 검증 { google, naver, github }
     */
    @Transactional
    public PostCreateResponseDTO createPost(final PostCreateRequestDTO requestDTO, final String authUsername) {

        Member findMember = memberRepository.findByUsernameOrElseThrow(authUsername);
        Post savePost = postRepository.save(requestDTO.toPostDomain(findMember));

        MultipartFile attacheFile = requestDTO.imageFile();

        AttachedFileCreateResponseDTO attachedFileCreateResponseDTO = null;
        try {
            if (attacheFile != null) {
                attachedFileCreateResponseDTO = attachedFileService.saveAttachedFile(attacheFile, savePost);
            }

        } catch (ApiException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("게시물 작성 중 오류가 발생: {}", e.getMessage());
            throw e;
        }

        return PostCreateResponseDTO.of(savePost, attachedFileCreateResponseDTO);
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
        List<Post> findAllPostData = postRepository.findAllByPostStateTypeOrderByCreatedAtDesc(PostStateType.ENABLE);
        return PostReadResponseDTO.of(findAllPostData);
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

        checkedUserPostAuthorValidate(findPostData.getMember().getId(), findMember.getId());

        findPostData.updateTitle(requestDTO);

        AttachedFileUpdateResponseDTO attachedFileUpdateResponseDTO = null;
        try {
            attachedFileUpdateResponseDTO = attachedFileService.updateAttacheFile(requestDTO.imageFile(), findPostData);

        } catch (ApiException e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("게시물 작성 중 오류가 발생: {}", e.getMessage());
            throw e;
        }

        return PostUpdateResponseDTO.of(findPostData, attachedFileUpdateResponseDTO);
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
        checkedUserPostAuthorValidate(findPostData.getMember().getId(), findMemberData.getId());

        AttachedFile findAttachedFile = attachedFileService.findByAttachedFile(findPostData);

        findPostData.deleteTitle(PostStateType.DISABLE);
        findAttachedFile.deleteAttachedFile(AttacheFileStatueType.DISABLE);
    }

    private void checkedUserPostAuthorValidate(Long postId, Long userId) {

        if (!Objects.equals(postId, userId)) {
            throw new PostUnauthorizedException();
        }
    }
}
