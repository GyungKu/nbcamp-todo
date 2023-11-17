package com.sparta.nbcamptodo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.nbcamptodo.dto.GlobalResponseDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            filterChain.doFilter(request, response);
        }  catch (SecurityException | MalformedJwtException | SignatureException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            GlobalResponseDto<String> responseDto = new GlobalResponseDto<>("유효하지 않은 토큰입니다.", "Invalid JWT signature");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            GlobalResponseDto<String> responseDto = new GlobalResponseDto<>("만료된 JWT token 입니다.", "Expired JWT token");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        } catch (UnsupportedJwtException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            GlobalResponseDto<String> responseDto = new GlobalResponseDto<>("지원되지 않는 JWT 토큰 입니다.", "Unsupported JWT token");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            GlobalResponseDto<String> responseDto = new GlobalResponseDto<>("잘못된 JWT 토큰 입니다.", "JWT claims is empty");
            response.getWriter().write(objectMapper.writeValueAsString(responseDto));
        }
    }
}