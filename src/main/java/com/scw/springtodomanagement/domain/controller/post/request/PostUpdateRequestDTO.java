package com.scw.springtodomanagement.domain.controller.post.request;

import com.scw.springtodomanagement.domain.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Schema(name = "Post Update Request DTO")
public record PostUpdateRequestDTO(

        @Schema(description = "제목", example = "2024-05-16 해야 할 일")
        @NotBlank(message = "제목의 입력 값이 없습니다.")
        @Length(max = 200, message = "제목의 입력 범위를 초과하였습니다.")
        String title,

        @Schema(description = "내용", example = "알고리즘 풀기")
        @NotBlank(message = "내용의 입력 값이 없습니다.")
        @Length(max = 255, message = "내용의 입력 범위를 초과하였습니다.")
        String content,

        @Schema(description = "수정할 이미지", example = "image.jpg")
        MultipartFile imageFile
) {
    public Post toPostDomain() {
        return Post.builder()
                .title(title)
                .content(content)
                .build();
    }
}
