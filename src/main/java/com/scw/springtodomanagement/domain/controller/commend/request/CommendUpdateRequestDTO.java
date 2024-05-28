package com.scw.springtodomanagement.domain.controller.commend.request;

import com.scw.springtodomanagement.domain.entity.Commend;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(name = "Commend Update Request DTO")
public record CommendUpdateRequestDTO(
        @Schema(description = "내용", example = "알고리즘 풀기")
        @NotBlank(message = "내용의 입력 값이 없습니다.")
        @Length(max = 255, message = "내용의 입력 범위를 초과하였습니다.")
        String content
) {
    public Commend toCommendDomain(Member member, Post findPost) {
        return Commend.builder()
                .contents(content)
                .member(member)
                .post(findPost)
                .build();
    }
}
