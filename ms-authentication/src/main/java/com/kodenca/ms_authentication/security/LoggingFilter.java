package com.kodenca.ms_authentication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
    
    private static final int MAX_BODY_SIZE = 10000;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        long start = System.currentTimeMillis();
        
        // Log básico del request
        log.info("=== REQUEST ===");
        log.info("Method: {} {}", request.getMethod(), request.getRequestURI());
        log.info("Content-Type: {}", request.getContentType());
        log.info("Content-Length: {}", request.getContentLength());
        
        // Log de headers (solo autorización para seguridad)
        log.info("--- Headers ---");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            
            // Solo loguear headers no sensibles
            if (!isSensitiveHeader(headerName)) {
                log.info("Header {}: {}", headerName, headerValue);
            } else {
                log.info("Header {}: [REDACTED]", headerName);
            }
        }
        
        // Log de parámetros
        log.info("--- Parameters ---");
        Collections.list(request.getParameterNames())
                .forEach(param -> {
                    String paramValue = request.getParameter(param);
                    if (isSensitiveParameter(param)) {
                        log.info("Parameter {}: [REDACTED]", param);
                    } else {
                        log.info("Parameter {}: {}", param, paramValue);
                    }
                });
        
        // Log del body (seguro)
        if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
            logRequestBodySecurely(request);
        }
        
        log.info("====================");
        
        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;
        
        // Log del response (simple, sin wrapper)
        log.info("=== RESPONSE ===");
        log.info("Status: {}", response.getStatus());
        log.info("Duration: {} ms", duration);
        log.info("ContentType: {}", response.getContentType());
        log.info("====================");
    }
    
    /**
     * Loguea el body de forma segura (simplificado)
     */
    private void logRequestBodySecurely(HttpServletRequest request) {
        try {
            int contentLength = request.getContentLength();
            
            // No loguear bodies muy grandes
            if (contentLength > MAX_BODY_SIZE) {
                log.info("--- Request Body ---");
                log.info("Body: [TOO LARGE - {} bytes]", contentLength);
                return;
            }
            
            String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
            
            // Verificar si contiene datos sensibles
            if (containsSensitiveData(body)) {
                log.info("--- Request Body ---");
                log.info("Body: [CONTAINS SENSITIVE DATA]");
            } else {
                log.info("--- Request Body ---");
                log.info("Body: {}", body);
            }
            
        } catch (Exception e) {
            log.warn("Could not read request body: {}", e.getMessage());
        }
    }
    
    /**
     * Verifica si el header es sensible
     */
    private boolean isSensitiveHeader(String headerName) {
        String lowerHeader = headerName.toLowerCase();
        return lowerHeader.contains("authorization") ||
               lowerHeader.contains("token") ||
               lowerHeader.contains("api-key") ||
               lowerHeader.contains("secret");
    }
    
    /**
     * Verifica si el parámetro es sensible
     */
    private boolean isSensitiveParameter(String paramName) {
        String lowerParam = paramName.toLowerCase();
        return lowerParam.contains("password") ||
               lowerParam.contains("token") ||
               lowerParam.contains("secret") ||
               lowerParam.contains("key");
    }
    
    /**
     * Verifica si el body contiene datos sensibles
     */
    private boolean containsSensitiveData(String body) {
        String lowerBody = body.toLowerCase();
        return lowerBody.contains("password") ||
               lowerBody.contains("token") ||
               lowerBody.contains("secret") ||
               lowerBody.contains("key") ||
               lowerBody.contains("credit");
    }
}
