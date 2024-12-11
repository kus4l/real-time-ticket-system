package org.ticket.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indicates that this class contains Spring configuration
public class WebConfig {

    // Defines a bean to configure CORS settings
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            // Configures CORS mappings to allow cross-origin requests
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow CORS on all endpoints
                        .allowedOrigins("http://localhost:5173") // Allow requests from this specific origin
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow specified HTTP methods
                        .allowedHeaders("*") // Allow all headers in requests
                        .allowCredentials(true); // Allow credentials (e.g., cookies, authorization headers)
            }
        };
    }
}