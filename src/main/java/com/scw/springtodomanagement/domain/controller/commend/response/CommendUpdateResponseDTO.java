package com.scw.springtodomanagement.domain.controller.commend.response;

import com.scw.springtodomanagement.domain.entity.Commend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@Schema(name = "Commend Update Response DTO")
public class CommendUpdateResponseDTO {

    private Long postId;
    private Long memberId;
    private Long commendId;
    private String contents;
    private String username;

    public static CommendUpdateResponseDTO of(Commend commend) {
        return CommendUpdateResponseDTO.builder()
                .postId(commend.getPost().getId())
                .memberId(commend.getMember().getId())
                .commendId(commend.getId())
                .contents(commend.getContents())
                .username(commend.getMember().getUserName())
                .build();
    }

}
