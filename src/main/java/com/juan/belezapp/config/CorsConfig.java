package com.juan.belezapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {

                String[] allowedOrigins;

                if ("dev".equals(activeProfile)) {
                    // Ambiente local
                    allowedOrigins = new String[] {
                            "http://localhost:4200"
                    };
                } else {
                    // Ambiente de produção
                    allowedOrigins = new String[] {
                            "https://belezapp-frontend.onrender.com"
                    };
                }

                registry.addMapping("/**")
                        .allowedOrigins(allowedOrigins)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
