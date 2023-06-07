package com.entando.springbootagenda.config;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${ALLOWED_API_ORIGIN}")
    private List<String> allowedApiOrigin;

    @Bean
    public WebMvcConfigurer getCorsConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                        .allowedOrigins(allowedApiOrigin.toArray(new String[allowedApiOrigin.size()]))
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
