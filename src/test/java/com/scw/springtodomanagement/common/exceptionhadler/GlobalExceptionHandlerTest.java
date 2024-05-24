package com.scw.springtodomanagement.common.exceptionhadler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.springtodomanagement.domain.controller.PostController;
import com.scw.springtodomanagement.domain.dto.request.PostCreateRequestDTO;
import com.scw.springtodomanagement.domain.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc(addFilters = false)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("요청 정보가 잘 못된 경우")
    void methodArgumentTypeMismatchExceptionTest() throws Exception {
        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .title("제목")
                .content("내용")
                .managerEmail("test@gmail.com")
                .password("1234asdf")
                .build();

        mockMvc.perform(get("/api/v1/posts/ffffff")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("올바르지 않은 요청 정보입니다."));
    }


    @Test
    @DisplayName("지원하지 않은 HTTP method 호출 할 경우")
    void httpRequestMethodNotSupportedExceptionTest() throws Exception {
        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .title("제목")
                .content("내용")
                .managerEmail("test@gmail.com")
                .password("1234asdf")
                .build();

        mockMvc.perform(delete("/api/v1/posts")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO))
                ).andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.message").value("올바르지 않는 요청 메소드입니다."));
    }

    @Test
    @DisplayName("올바르지 않은 요청 URL")
    void noResourceFoundExceptionTest() throws Exception {
        PostCreateRequestDTO requestDTO = PostCreateRequestDTO.builder()
                .title("제목")
                .content("내용")
                .managerEmail("test@gmail.com")
                .password("1234asdf")
                .build();

        mockMvc.perform(post("/api/v1/posts1")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(requestDTO))
                ).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("올바르지 않은 요청 URL입니다."));
    }


}