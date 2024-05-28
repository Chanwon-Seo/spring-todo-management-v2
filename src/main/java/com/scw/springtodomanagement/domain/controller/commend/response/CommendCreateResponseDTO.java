
package com.scw.springtodomanagement.domain.controller.commend.response;

import com.scw.springtodomanagement.domain.entity.Commend;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Schema(name = "Commend Create Response DTO")
public class CommendCreateResponseDTO {

    private Long postId;
    private Long memberId;
    private Long commendId;
    private String contents;
    private String username;

    public static CommendCreateResponseDTO of(Commend commend) {
        return CommendCreateResponseDTO.builder()
                .postId(commend.getPost().getId())
                .memberId(commend.getMember().getId())
                .commendId(commend.getId())
                .contents(commend.getContents())
                .username(commend.getMember().getUserName())
                .build();
    }

}
