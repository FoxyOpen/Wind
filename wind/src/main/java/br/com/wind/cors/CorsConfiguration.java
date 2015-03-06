package br.com.wind.cors;

import java.util.Set;

public interface CorsConfiguration {
    
    Set<String> getAllowedOrigins();
    
    int getCorsMaxAge();
    
    boolean isAllowCredentials();
    
    String getAllowedHeaders();
    
    String getAllowedMethods();
    
    String getExposedHeaders();
    
}
