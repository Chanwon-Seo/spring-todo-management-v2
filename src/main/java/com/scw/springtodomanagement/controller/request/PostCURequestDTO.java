package com.scw.springtodomanagement.controller.request;

import com.scw.springtodomanagement.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostCURequestDTO {

    private String title;

    private String content;

    private String managerEmail;

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
