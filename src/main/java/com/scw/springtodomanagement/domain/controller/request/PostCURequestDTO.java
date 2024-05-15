package com.scw.springtodomanagement.domain.controller.request;

import com.scw.springtodomanagement.domain.entity.Post;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@AllArgsConstructor
public class PostCURequestDTO {

    @NotBlank(message = "제목의 입력 값이 없습니다.")
    @Length(max = 200, message = "제목의 입력 범위를 초과하였습니다.")
    private String title;

    @NotBlank(message = "내용의 입력 값이 없습니다.")
    @Length(max = 255, message = "내용의 입력 범위를 초과하였습니다.")
    private String content;

    @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "이메일 형식에 맞지 않습니다.")
    @NotBlank(message = "이메일의 입력 값이 없습니다.")
    @Length(max = 255, message = "이메일 입력 범위를 초과하였습니다.")
    private String managerEmail;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자 조합 8자리 이상이어야 합니다.")
    @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
    @Length(min = 8, max = 200, message = "비밀번호 입력 조건을 맞춰주세요")
    private String password;

    public Post toPostDomain() {
        return Post.builder()
                .title(title)
                .content(content)
                .managerEmail(managerEmail)
                .password(password)
                .build();
    }
}
