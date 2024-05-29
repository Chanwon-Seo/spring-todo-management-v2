package com.scw.springtodomanagement.domain.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.springtodomanagement.domain.controller.post.*;
import com.scw.springtodomanagement.domain.controller.post.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.request.PostUpdateRequestDTO;
import com.scw.springtodomanagement.domain.controller.post.response.PostReadResponseDTO;
import com.scw.springtodomanagement.domain.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("게시글 등록 - 성공")
    void createPost() throws Exception {
        //given
        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .title("제목")
                .content("내용")
                .managerEmail("test@gmail.com")
                .password("1234asdf")
                .build();

        //when

        //then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                ).andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("특정 포스트 조회 성공")
    void findPostById() throws Exception {
        // given
        PostReadResponseDTO responseDTO = PostReadResponseDTO.builder()
                .id(1L)
                .title("제목")
                .content("내용")
                .managerEmail("test@gmail.com")
                .build();

        given(postService.findPostById(any(Long.class))).willReturn(responseDTO);

        // when & then
        mockMvc.perform(get("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.title").value("제목"))
                .andExpect(jsonPath("$.data.content").value("내용"))
                .andExpect(jsonPath("$.data.managerEmail").value("test@gmail.com"));
    }


    @Test
    @DisplayName("게시글 다건 조회 - 성공")
    void findAll() throws Exception {
        List<PostReadResponseDTO> responseDTOList = List.of(
                PostReadResponseDTO.builder()
                        .id(1L)
                        .title("제목1")
                        .content("내용1")
                        .managerEmail("test1@gmail.com")
                        .build(),
                PostReadResponseDTO.builder()
                        .id(2L)
                        .title("제목2")
                        .content("내용2")
                        .managerEmail("test2@gmail.com")
                        .build()
        );

        given(postService.findAll()).willReturn(responseDTOList);

        // when & then
        mockMvc.perform(get("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value("제목1"))
                .andExpect(jsonPath("$.data[0].content").value("내용1"))
                .andExpect(jsonPath("$.data[0].managerEmail").value("test1@gmail.com"))
                .andExpect(jsonPath("$.data[1].title").value("제목2"))
                .andExpect(jsonPath("$.data[1].content").value("내용2"))
                .andExpect(jsonPath("$.data[1].managerEmail").value("test2@gmail.com"));
    }

    @Test
    @DisplayName("게시글 수정 - 성공")
    void updatePost() throws Exception {
        //given
        PostUpdateRequestDTO requestDTO = PostUpdateRequestDTO.builder()
                .title("제목1")
                .content("내용1")
                .build();

        //when

        //then
        mockMvc.perform(put("/api/v1/posts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("게시글 삭제 - 성공")
    void deletePost() throws Exception {
        // Given
        Long postId = 1L; // 삭제할 게시글의 ID

        // When
        mockMvc.perform(delete("/api/v1/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(1L)))
                .andExpect(status().isOk());

    }

    /**
     * 실패
     */
    @Test
    @DisplayName("게시글 등록 - 실패  문제 [제목 미입력]")
    void createPostTitleNull() throws Exception {
        //given
        PostCreateRequestDTO request = PostCreateRequestDTO.builder()
                .title("")
                .content("내용")
                .managerEmail("test@gmail.com")
                .password("1234asdf")
                .build();

        //when

        //then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.title").value("제목의 입력 값이 없습니다."));
    }

    @Test
    @DisplayName("게시글 등록 - 실패 요청 정보 없음 [전체 미입력]")
    void createPostAllNull() throws Exception {
        //given
        PostCreateRequestDTO request = PostCreateRequestDTO.builder()
                .title("")
                .content("")
                .managerEmail("")
                .password("")
                .build();

        //when

        //then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("올바르지 않은 요청 정보입니다."));
    }

    @Test
    @DisplayName("게시글 등록 - 제목의 길이가 초과 될 경우")
    void passwordNotEqualsException() throws Exception {
        //given
        PostCreateRequestDTO request = PostCreateRequestDTO.builder()
                .title("1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                        "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                        "1")
                .content("내용")
                .managerEmail("test@gmail.com")
                .password("1234asdf")
                .build();

        //when

        //then
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(request))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.data.title").value("제목의 입력 범위를 초과하였습니다."));
    }
}