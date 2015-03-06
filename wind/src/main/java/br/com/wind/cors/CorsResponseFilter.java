package br.com.wind.cors;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CorsResponseFilter implements ContainerResponseFilter {
    
    @Inject
    private CorsConfiguration corsProvider;
    
    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) throws IOException {
        String origin = requestContext.getHeaderString(CorsHeaders.ORIGIN.toString());
        if (origin == null || HttpMethod.OPTIONS.toString().equals(requestContext.getMethod()) || requestContext.getProperty("cors.failure") != null) {
            return;
        }
        
        responseContext.getHeaders().putSingle(CorsHeaders.ACCESS_CONTROL_ALLOW_ORIGIN.toString(), origin);
        
        if (corsProvider.isAllowCredentials()) {
            responseContext.getHeaders().putSingle(CorsHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS.toString(), Boolean.TRUE.toString());
        }
        
        if (corsProvider.getExposedHeaders() != null) {
            responseContext.getHeaders().putSingle(CorsHeaders.ACCESS_CONTROL_EXPOSE_HEADERS.toString(), corsProvider.getExposedHeaders());
        }
    }
    
}
