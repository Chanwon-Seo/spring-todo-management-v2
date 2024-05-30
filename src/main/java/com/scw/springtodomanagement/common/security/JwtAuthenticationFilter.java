package com.scw.springtodomanagement.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.springtodomanagement.common.exception.errorcode.MemberErrorCode;
import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import com.scw.springtodomanagement.common.global.response.RestApiResponse;
import com.scw.springtodomanagement.common.jwt.JwtUtil;
import com.scw.springtodomanagement.common.jwt.TokenDTO;
import com.scw.springtodomanagement.common.security.dto.AuthRequestDto;
import com.scw.springtodomanagement.domain.entity.Member;
import com.scw.springtodomanagement.domain.entity.MemberLoginHistory;
import com.scw.springtodomanagement.domain.entity.enums.MemberLoginHistoryStatus;
import com.scw.springtodomanagement.domain.repository.MemberLoginHistoryRepository;
import com.scw.springtodomanagement.domain.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.List;

import static com.scw.springtodomanagement.common.consts.JwtConstants.*;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final MemberLoginHistoryRepository memberLoginHistoryRepository;

    public JwtAuthenticationFilter(ObjectMapper objectMapper, JwtUtil jwtUtil, MemberLoginHistoryRepository memberLoginHistoryRepository, MemberRepository memberRepository) {
        this.objectMapper = objectMapper;
        this.jwtUtil = jwtUtil;
        this.memberRepository = memberRepository;
        this.memberLoginHistoryRepository = memberLoginHistoryRepository;
        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthRequestDto requestDto = new ObjectMapper().readValue(request.getInputStream(), AuthRequestDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            requestDto.getUsername(),
                            requestDto.getPassword(),
                            null
                    )
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        log.info("로그인 성공 및 JWT 토큰 발행");
        Member member = memberRepository.findByUsernameOrElseThrow(authResult.getName());

        makeLoginSuccessResponse(response, authResult);

        // 로그인 성공 이력 저장
        saveLoginHistory(member, MemberLoginHistoryStatus.SUCCESS);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        log.error("로그인 실패");
        ErrorResponse errorResponse = ErrorResponse.of(MemberErrorCode.USER_NOT_FOUND);
        String body = objectMapper.writeValueAsString(errorResponse);
        response.setStatus(errorResponse.getCode());
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(body);

        // 로그인 실패 이력 저장
        saveLoginHistory(null, MemberLoginHistoryStatus.FAILURE);
    }

    private void makeLoginSuccessResponse(HttpServletResponse response, Authentication authResult)
            throws IOException {
        RestApiResponse apiResponse = RestApiResponse.of("로그인 성공");
        String body = objectMapper.writeValueAsString(apiResponse);

        AuthenticationUser principalUser = (AuthenticationUser) authResult.getPrincipal();
        String username = principalUser.getUsername();
        List<String> authorities = principalUser.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        TokenDTO tokenDto = jwtUtil.generateAccessTokenAndRefreshToken(username, authorities);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.addHeader(ACCESS_TOKEN_HEADER, tokenDto.getAccessToken());
        response.addHeader(REFRESH_TOKEN_HEADER, tokenDto.getRefreshToken());
        response.getWriter().write(body);
    }
    private void saveLoginHistory(Member member, MemberLoginHistoryStatus status) {
        if (member != null) {
            memberLoginHistoryRepository.save(MemberLoginHistory.builder()
                    .member(member)
                    .username(member.getUserName())
                    .memberLoginHistoryStatus(status)
                    .build()
            );
        }

    }

}