package com.scw.springtodomanagement.domain.controller.commend.response;

import com.scw.springtodomanagement.domain.entity.Commend;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(name = "Commend Read Response DTO")
public class CommendReadResponseDTO {
    private Long id;

    private String contents;

    private String username;

    public CommendReadResponseDTO(Commend commend) {
        this.id = commend.getId();
        this.contents = commend.getContents();
        this.username = commend.getMember().getUserName();
    }
}
