package com.hellog.global.exception;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GlobalExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            ErrorResponse errorResponse =
                    new ErrorResponse(e.getStatus().value(), e.getMessage());
            response.setStatus(e.getStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(errorResponse.toString());
        }
    }

}