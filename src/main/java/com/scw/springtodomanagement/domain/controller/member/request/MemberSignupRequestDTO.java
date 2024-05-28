package com.scw.springtodomanagement.domain.controller.member.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(name = "Member Signup Request DTO")
public record MemberSignupRequestDTO(

        @Schema(description = "별명", example = "별명")
        @NotBlank(message = "별명의 입력 값이 없습니다.")
        @Length(max = 20, message = "별명의 입력 범위를 초과하였습니다.")
        String nickname,

        @Schema(description = "이메일", example = "test@gmail.com")
        @Email(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
                message = "이메일 형식에 맞지 않습니다.")
        @NotBlank(message = "이메일의 입력 값이 없습니다.")
        @Length(max = 255, message = "이메일 입력 범위를 초과하였습니다.")
        String username,

        @Schema(description = "비밀번호", example = "1234asdf@")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$",
                message = "비밀번호는 영문, 숫자, 특수문자 조합 8자리 이상이어야 합니다.")
        @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
        @Length(min = 8, max = 200, message = "비밀번호 입력 조건을 맞춰주세요")
        String password
) {
}
