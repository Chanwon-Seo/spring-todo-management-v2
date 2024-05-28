package com.scw.springtodomanagement.domain.service;

import com.scw.springtodomanagement.common.exception.errorcode.PostErrorCode;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.domain.controller.post.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostCreateResponseDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostReadResponseDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostUpdateResponseDTO;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import com.scw.springtodomanagement.domain.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("게시글 테스트")
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    /**
     * 성공
     */
    @Test
    @DisplayName("게시글 등록 - 성공")
    void createPost() {
        // given
        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .title("제목입니다1.")
                .content("내용입니다.")
                .build();
//        Post postDomain = requestDTO.toPostDomain();

        PostCreateResponseDTO originResponseDto = PostCreateResponseDTO.builder()
                .title(requestDTO.title())
                .content(requestDTO.content())
                .build();


//        given(postRepository.save(any(Post.class))).willReturn(postDomain);

        // when
        PostCreateResponseDTO newResponseDto = postService.createPost(requestDTO, "username");

        // then
        Assertions.assertEquals(originResponseDto.getId(), newResponseDto.getId());
        Assertions.assertEquals(originResponseDto.getTitle(), newResponseDto.getTitle());
        Assertions.assertEquals(originResponseDto.getContent(), newResponseDto.getContent());
        Assertions.assertEquals(originResponseDto.getManagerEmail(), newResponseDto.getManagerEmail());
    }

    @Test
    @DisplayName("게시글 단건 조회 - 성공")
    void findByIdOrElseThrow() {
        // given
        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .title("제목입니다1.")
                .content("내용입니다.")
                .build();

//        Post postDomain = requestDTO.toPostDomain();

        PostCreateResponseDTO originResponseDto = PostCreateResponseDTO.builder()
                .title(requestDTO.title())
                .content(requestDTO.content())
//                .createdAt()
//                .lastModifiedAt()
                .build();

//        given(postRepository.findByIdOrElseThrow(any(Long.class))).willReturn(postDomain);

        // when
        PostReadResponseDTO newResponseDto = postService.findPostById(1L);

        // then
        Assertions.assertEquals(originResponseDto.getId(), newResponseDto.getId());
        Assertions.assertEquals(originResponseDto.getTitle(), newResponseDto.getTitle());
        Assertions.assertEquals(originResponseDto.getContent(), newResponseDto.getContent());
        Assertions.assertEquals(originResponseDto.getManagerEmail(), newResponseDto.getManagerEmail());
    }

    @Test
    @DisplayName("게시글 다건 조회 - 성공")
    void getSchedules() {
        // given
        List<PostReadResponseDTO> originResponseDto = List.of(
                PostReadResponseDTO.builder()
                        .title("제목입니다1.")
                        .content("내용입니다1.")
                        .managerEmail("test1@gmail.com")
                        .build(),
                PostReadResponseDTO.builder()
                        .title("제목입니다2.")
                        .content("내용입니다2.")
                        .managerEmail("test2@gmail.com")
                        .build());
        List<Post> postList = List.of(
                Post.builder()
                        .title("제목입니다1.")
                        .content("내용입니다1.")
//                        .managerEmail("test1@gmail.com")
                        .build(),
                Post.builder()
                        .title("제목입니다2.")
                        .content("내용입니다2.")
//                        .managerEmail("test2@gmail.com")
                        .build()
        );

        given(postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))).willReturn(postList);

        // when
        List<PostReadResponseDTO> newResponseDto = postService.findAll();

        // then
        for (int i = 0; i < postList.size(); i++) {
            Assertions.assertEquals(originResponseDto.get(i).getTitle(), newResponseDto.get(i).getTitle());
            Assertions.assertEquals(originResponseDto.get(i).getContent(), newResponseDto.get(i).getContent());
            Assertions.assertEquals(originResponseDto.get(i).getManagerEmail(), newResponseDto.get(i).getManagerEmail());
        }
    }

    @Test
    @DisplayName("게시글 수정 - 성공")
    void updatePost() {
        // given
        PostUpdateRequestDTO requestDTO = PostUpdateRequestDTO.builder()
                .title("수정 제목입니다1.")
                .content("수정 내용입니다1.")
                .build();

        PostUpdateResponseDTO originResponseDto = PostUpdateResponseDTO.builder()
                .title("수정 제목입니다1.")
                .content("수정 내용입니다1.")
                .managerEmail("test@gmail.com")
                .build();

        given(postRepository.findByIdOrElseThrow(any(Long.class))).willReturn(requestDTO.toPostDomain());

        // when
        PostUpdateResponseDTO newResponseDto = postService.updatePost(1L, requestDTO, "test@gmail.com");

        // then
        Assertions.assertEquals(originResponseDto.getId(), newResponseDto.getId());
        Assertions.assertEquals(originResponseDto.getTitle(), newResponseDto.getTitle());
        Assertions.assertEquals(originResponseDto.getContent(), newResponseDto.getContent());
        Assertions.assertEquals(originResponseDto.getManagerEmail(), newResponseDto.getManagerEmail());
    }

    @Test
    @DisplayName("게시글 삭제 - 성공")
    void deletePost() {
        Member build = Member.builder()
                .userName("username")
                .nickName("nickname")
                .password("123123123asfasdfasdf")
                .build();
        Post post = new Post(1L, "제목1", "내용1", build, PostStateType.ENABLE);

        when(postRepository.findByIdOrElseThrow(post.getId())).thenReturn(post);
    }

    /**
     * 실패
     */
    @Test
    @DisplayName("게시글 단건 조회 - 실패 [존재하지 않는 게시물입니다.]")
    void findPostByIdException() {
        // given
        given(postRepository.findByIdOrElseThrow(any(Long.class)))
                .willThrow(new ApiException(PostErrorCode.NOT_FOUND_POST));

        // when

        // then
        ApiException exception = assertThrows(ApiException.class, () ->
                postService.findPostById(2L));

        Assertions.assertEquals(
                PostErrorCode.NOT_FOUND_POST.getDescription(),
                exception.getMessage());
    }

    @Test
    @DisplayName("일치하지 않는 비밀번호 - 실패 [일치하지 않는 비밀번호 입니다.]")
    void passwordNotEqualsException() {
        PostUpdateRequestDTO originalRequestDTO = PostUpdateRequestDTO.builder()
                .title("제목입니다1.")
                .content("내용입니다1.")
                .build();

        PostUpdateRequestDTO newRequestDTO = PostUpdateRequestDTO.builder()
                .title("수정 제목입니다1.")
                .content("수정 내용입니다1.")
                .build();

        given(postRepository.findByIdOrElseThrow(any(Long.class))).willReturn(originalRequestDTO.toPostDomain());

        // when
        ApiException exception = assertThrows(ApiException.class, () ->
                postService.updatePost(1L, newRequestDTO, "test@gmail.com")
        );

        Assertions.assertEquals(
                PostErrorCode.PASSWORD_VERIFY_FAIL.getDescription(),
                exception.getMessage()
        );
    }

    @Test
    @DisplayName("지원되지 않는 이메일 - 실패 [지원되지 않는 이메일입니다.] / gmail.com / naver.com / github.com")
    void unsupportedEmailDomainException() {
        // given
        PostCreateRequestDTO requestDTO2 = PostCreateRequestDTO.builder()
                .title("제목입니다1.")
                .content("내용입니다.")
                .build();

        // when
        ApiException exception = assertThrows(ApiException.class, () ->
                postService.createPost(requestDTO2, "username")
        );

        // then
        Assertions.assertEquals(
                PostErrorCode.UNSUPPORTED_EMAIL_DOMAIN.getDescription(),
                exception.getMessage()
        );


    }


}