package com.scw.springtodomanagement.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        tags = {
                @Tag(name = "01. Test", description = "Start APIs"),
                @Tag(name = "02. Post", description = "해야 할 일 APIs"),
                @Tag(name = "03. Image", description = "이미지 APIs"),
                @Tag(name = "04. User", description = "회원 APIs"),
                @Tag(name = "05. Commend", description = "댓글 APIs"),
        }
)
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("입문주차 개인과제")
                .description("나만의 일정 관리 앱 서버 만들기")
                .version("1.0.0");
    }
}