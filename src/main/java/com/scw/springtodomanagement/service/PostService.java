package com.scw.springtodomanagement.service;

import com.scw.springtodomanagement.controller.request.PostCURequestDTO;
import com.scw.springtodomanagement.controller.response.PostResponseDTO;
import com.scw.springtodomanagement.entity.Post;
import com.scw.springtodomanagement.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponseDTO createPost(PostCURequestDTO requestDTO) {
        Post savePost = postRepository.save(requestDTO.toPostDomain());

        return getPostResponseDTO(savePost);
    }


    public PostResponseDTO findPostById(Long id) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);
        return getPostResponseDTO(findPostData);
    }

    public List<PostResponseDTO> findAll() {
        return postRepository.findAll().stream()
                .map(PostResponseDTO::new)
                .toList();
    }

    @Transactional
    public PostResponseDTO updatePost(Long id, PostCURequestDTO requestDTO) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);

        passwordValidation(requestDTO.getPassword(), findPostData.getPassword());

        findPostData.updateTitle(requestDTO);
        return getPostResponseDTO(findPostData);
    }

    @Transactional
    public void deletePost(Long id, Map<String, String> requestPassword) {
        Post findPostData = postRepository.findByIdOrElseThrow(id);

        passwordValidation(requestPassword.get("password"), findPostData.getPassword());

        postRepository.delete(findPostData);
    }

    /**
     * password
     */
    private void passwordValidation(String inputPassword, String findPassword) {
        if (!inputPassword.equals(findPassword)) {
            throw new IllegalArgumentException("일치하지 않는 비밀번호입니다.");
        }
    }

    /**
     * responseMethod
     * return PostResponseDTO
     */
    private PostResponseDTO getPostResponseDTO(Post savePostData) {
        return PostResponseDTO.builder()
                .id(savePostData.getId())
                .title(savePostData.getTitle())
                .content(savePostData.getContent())
                .manager(savePostData.getManagerEmail())
                .createdAt(savePostData.getCreatedAt())
                .lastModifiedAt(savePostData.getLastModifiedAt())
                .build();
    }
}
