package org.ece.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    private static final String TBDBANK_DOMAIN = "https://www.tbdbank.me";
    private static final String TBDBANK_DOMAIN_2 = "tbdbank.me";
    private static final String SANTHOSH_DOMAIN = "https://www.santhoshprojects.me";
    private static final String SANTHOSH_DOMAIN_2 = "santhoshprojects.me";
    private static final String ANIRUDH_IP = "192.168.0.28:4200";
    private static final String ANIRUDH_IP_2 = "192.168.0.28";
    private static final String ANIRUDH_IP_3 = "localhost:4200";

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(true)
                        .allowedOrigins(TBDBANK_DOMAIN, TBDBANK_DOMAIN_2, SANTHOSH_DOMAIN, SANTHOSH_DOMAIN_2,
                                ANIRUDH_IP, ANIRUDH_IP_2, ANIRUDH_IP_3);
            }
        };
    }
}
