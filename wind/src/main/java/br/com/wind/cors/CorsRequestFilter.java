package br.com.wind.cors;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CorsRequestFilter implements ContainerRequestFilter {
    
    @Inject
    private CorsConfiguration cors;
    
    @Override
    public void filter(final ContainerRequestContext requestContext) throws IOException {
        String origin = requestContext.getHeaderString(CorsHeaders.ORIGIN.toString());
        if (origin == null) {
            return;
        }
        
        checkOrigin(requestContext, origin);
        
        if (HttpMethod.OPTIONS.equals(requestContext.getMethod())) {
            configureCors(origin, requestContext);
        }
    }
    
    private void configureCors(final String origin, final ContainerRequestContext requestContext) throws IOException {
        Response.ResponseBuilder builder = Response.ok();
        builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), origin);
        
        if (cors.isAllowCredentials()) {
            builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS.toString(), Boolean.TRUE.toString());
        }
        
        String requestMethods = requestContext.getHeaderString(CorsHeaders.ACCESS_CONTROL_REQUEST_METHOD.toString());
        if (requestMethods != null) {
            if (cors.getAllowedMethods() != null) {
                requestMethods = cors.getAllowedMethods();
            }
            
            builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_METHODS.toString(), requestMethods);
        }
        
        String allowHeaders = requestContext.getHeaderString(CorsHeaders.ACCESS_CONTROL_REQUEST_HEADERS.toString());
        if (allowHeaders != null) {
            if (cors.getAllowedHeaders() != null) {
                allowHeaders = cors.getAllowedHeaders();
            }
            
            builder.header(CorsHeaders.ACCESS_CONTROL_ALLOW_HEADERS.toString(), allowHeaders);
        }
        
        if (cors.getCorsMaxAge() > -1) {
            builder.header(CorsHeaders.ACCESS_CONTROL_MAX_AGE.toString(), cors.getCorsMaxAge());
        }
        
        requestContext.abortWith(builder.build());
    }
    
    private void checkOrigin(final ContainerRequestContext requestContext, final String origin) {
        if (!cors.getAllowedOrigins().contains("*") && !cors.getAllowedOrigins().contains(origin)) {
            requestContext.setProperty("cors.failure", true);
            
            throw new ForbiddenException("Origin not allowed: " + origin);
        }
    }
    
}