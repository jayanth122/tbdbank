package org.ece.configuration;

import lombok.Getter;
import org.ece.dto.AccessType;
import org.ece.dto.SessionData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Getter
@PropertySources({
        @PropertySource("classpath:application.properties"),
        @PropertySource("classpath:validation.properties")
})
public class DataSouceConfig {

    @Value("#{${valid.userNames}}")
    private Map<AccessType, String> validUserNames;

    private Map<String, SessionData> session = new HashMap<>();
}
