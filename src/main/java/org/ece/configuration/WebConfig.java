package org.ece.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig {
    private static final String TBDBANK_DOMAIN = "https://www.tbdbank.me";
    private static final String TBDBANK_DOMAIN_2 = "tbdbank.me";
    private static final String SANTHOSH_DOMAIN = "https://www.santhoshprojects.me";
    private static final String SANTHOSH_DOMAIN_2 = "santhoshprojects.me";

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins(TBDBANK_DOMAIN, TBDBANK_DOMAIN_2, SANTHOSH_DOMAIN, SANTHOSH_DOMAIN_2);
            }
        };
    }
}
