package com.scw.springtodomanagement.domain.controller.post.request;

import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Schema(name = "Post Create Request DTO")
public record PostCreateRequestDTO(
        @Schema(description = "제목", example = "2024-05-16 해야 할 일")
        @NotBlank(message = "제목의 입력 값이 없습니다.")
        @Length(max = 200, message = "제목의 입력 범위를 초과하였습니다.")
        String title,

        @Schema(description = "내용", example = "알고리즘 풀기")
        @NotBlank(message = "내용의 입력 값이 없습니다.")
        @Length(max = 255, message = "내용의 입력 범위를 초과하였습니다.")
        String content,

        @Schema(description = "첨부할 이미지", example = "image.jpg")
        MultipartFile imageFile
) {
    public Post toPostDomain(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .member(member)
                .postStateType(PostStateType.ENABLE)
                .build();
    }
}
