package com.scw.springtodomanagement.domain.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDeleteRequestDTO {

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9]).{8,}$",
            message = "비밀번호는 영문, 숫자, 특수문자 조합 8자리 이상이어야 합니다.")
    @NotBlank(message = "비밀번호의 입력 값이 없습니다.")
    @Length(min = 8, max = 200, message = "비밀번호 입력 조건을 맞춰주세요")
    private String password;
}
