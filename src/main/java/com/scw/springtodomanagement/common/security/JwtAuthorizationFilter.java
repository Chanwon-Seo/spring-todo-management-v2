package com.scw.springtodomanagement.common.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scw.springtodomanagement.common.exception.ApiException;
import com.scw.springtodomanagement.common.exception.errorcode.MemberErrorCode;
import com.scw.springtodomanagement.common.global.response.ErrorResponse;
import com.scw.springtodomanagement.common.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.scw.springtodomanagement.common.consts.JwtConstants.ACCESS_TOKEN_HEADER;

@Slf4j(topic = "JWT 검증 및 인가")
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AuthenticationUserService authenticationUserService;
    private final ObjectMapper objectMapper;

    public JwtAuthorizationFilter(JwtUtil jwtUtil, AuthenticationUserService authenticationUserService, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.authenticationUserService = authenticationUserService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String accessTokenValue = jwtUtil.getAccessTokenFromHeader(req);
        String refreshTokenValue = jwtUtil.getRefreshTokenFromHeader(req);

        if (StringUtils.hasText(accessTokenValue)) {
            // Access 만료 체크
            if (!jwtUtil.validateToken(accessTokenValue)) {
                log.error("Access Token이 만료되었습니다.");

                // refresh 체크 및 유효성 검사
                if (StringUtils.hasText(refreshTokenValue) && jwtUtil.validateToken(refreshTokenValue)) {

                    String username = jwtUtil.getUserInfoFromToken(refreshTokenValue).getSubject();
                    String newAccessToken = jwtUtil.createAccessToken(username);

                    res.setHeader(ACCESS_TOKEN_HEADER, newAccessToken);
                    log.info("새로운 Access Token이 생성되어 응답 헤더에 설정되었습니다.");

                    setAuthentication(username);
                } else {
                    // Refresh Token이 존재하지 않거나 만료된 경우, 401 Unauthorized 응답을 전송합니다.
                    log.error("Refresh Token이 없거나 만료되었습니다. 접근이 거부되었습니다.");
                    res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Token과 Refresh Token이 만료되었습니다.");
                    return;
                }
            } else {
                //access 유효한 경우
                Claims info = jwtUtil.getUserInfoFromToken(accessTokenValue);
                setAuthentication(info.getSubject());
            }
        }

        filterChain.doFilter(req, res);
    }


    // 인증 처리
    public void setAuthentication(String username) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(username);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String username) {
        UserDetails userDetails = authenticationUserService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}