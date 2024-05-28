package com.scw.springtodomanagement.domain.controller.member;

import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import com.scw.springtodomanagement.common.global.response.RestApiResponse;
import com.scw.springtodomanagement.domain.controller.member.request.MemberSignupRequestDTO;
import com.scw.springtodomanagement.domain.controller.member.response.MemberSignupResponseDTO;
import com.scw.springtodomanagement.domain.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.scw.springtodomanagement.common.statuscode.StatusCode.CREATED;

@Tag(name = "04. User")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "회원가입을 위한 Api\nschema에 있는 정보는 모두 필수 값이어야 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "1. 중복된 이메일이 있는 경우\n\n" +
                    "2. 필수 요청 정보가 비어있을 경우\n\n" +
                    "3. gmail.com, naver.com, github.com 이 외에 지원하지 않는 이메일\n\n" +
                    "(응답 결과에 data는 없음)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    @PostMapping("/signup")
    public ResponseEntity<RestApiResponse<MemberSignupResponseDTO>> signup(
            @Valid @RequestBody MemberSignupRequestDTO memberSignupRequestDTO) {
        MemberSignupResponseDTO responseDTO = memberService.signup(memberSignupRequestDTO);

        return ResponseEntity.status(CREATED.code)
                .body(RestApiResponse.of(CREATED.code, responseDTO));
    }

}
