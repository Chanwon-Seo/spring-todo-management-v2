package com.scw.springtodomanagement.domain.controller.post.request;

import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import com.scw.springtodomanagement.domain.entity.enums.PostStateType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(name = "Post Request DTO")
public record PostCreateRequestDTO(
        @Schema(description = "제목", example = "2024-05-16 해야 할 일")
        @NotBlank(message = "제목의 입력 값이 없습니다.")
        @Length(max = 200, message = "제목의 입력 범위를 초과하였습니다.")
        String title,

        @Schema(description = "내용", example = "알고리즘 풀기")
        @NotBlank(message = "내용의 입력 값이 없습니다.")
        @Length(max = 255, message = "내용의 입력 범위를 초과하였습니다.")
        String content

//        @Schema(description = "이메일", example = "test@gmail.com")
//        @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
//                message = "이메일 형식에 맞지 않습니다.")
//        @NotBlank(message = "이메일의 입력 값이 없습니다.")
//        @Length(max = 255, message = "이메일 입력 범위를 초과하였습니다.")
//        String managerEmail,
//
//        @Schema(description = "비밀번호", example = "1234asdf@")
//        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$",
//                message = "비밀번호는 영문, 숫자, 특수문자 조합 8자리 이상이어야 합니다.")
//        @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
//        @Length(min = 8, max = 200, message = "비밀번호 입력 조건을 맞춰주세요")
//        String password
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
