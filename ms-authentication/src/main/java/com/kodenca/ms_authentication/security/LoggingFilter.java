package com.kodenca.ms_authentication.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

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
        
        // Wrap request y response para poder leer el body múltiples veces
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);
        
        // Log básico del request
        log.info("=== REQUEST ===");
        log.info("Method: {} {}", requestWrapper.getMethod(), requestWrapper.getRequestURI());
        log.info("Content-Type: {}", requestWrapper.getContentType());
        log.info("Content-Length: {}", requestWrapper.getContentLength());
        
        // Log de headers (solo autorización para seguridad)
        log.info("--- Headers ---");
        Enumeration<String> headerNames = requestWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = requestWrapper.getHeader(headerName);
            
            // Solo loguear headers no sensibles
            if (!isSensitiveHeader(headerName)) {
                log.info("Header {}: {}", headerName, headerValue);
            } else {
                log.info("Header {}: [REDACTED]", headerName);
            }
        }
        
        // Log de parámetros
        log.info("--- Parameters ---");
        Collections.list(requestWrapper.getParameterNames())
                .forEach(param -> {
                    String paramValue = requestWrapper.getParameter(param);
                    if (isSensitiveParameter(param)) {
                        log.info("Parameter {}: [REDACTED]", param);
                    } else {
                        log.info("Parameter {}: {}", param, paramValue);
                    }
                });
        
        log.info("====================");
        
        // Ejecutar el resto de la cadena de filtros con los wrappers
        filterChain.doFilter(requestWrapper, responseWrapper);
        
        // Log del body del request (después de que se procese)
        if ("POST".equalsIgnoreCase(requestWrapper.getMethod()) || "PUT".equalsIgnoreCase(requestWrapper.getMethod())) {
            logRequestBodySecurely(requestWrapper);
        }

        long duration = System.currentTimeMillis() - start;
        
        // Log del response
        log.info("=== RESPONSE ===");
        log.info("Status: {}", responseWrapper.getStatus());
        log.info("Duration: {} ms", duration);
        log.info("ContentType: {}", responseWrapper.getContentType());
        log.info("====================");
        
        // Importante: copiar el response de vuelta al cliente
        responseWrapper.copyBodyToResponse();
    }
    
    /**
     * Loguea el body de forma segura usando ContentCachingRequestWrapper
     */
    private void logRequestBodySecurely(ContentCachingRequestWrapper requestWrapper) {
        try {
            byte[] content = requestWrapper.getContentAsByteArray();
            
            if (content.length == 0) {
                return;
            }
            
            // No loguear bodies muy grandes
            if (content.length > MAX_BODY_SIZE) {
                log.info("--- Request Body ---");
                log.info("Body: [TOO LARGE - {} bytes]", content.length);
                return;
            }
            
            String body = new String(content, StandardCharsets.UTF_8);
            
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
