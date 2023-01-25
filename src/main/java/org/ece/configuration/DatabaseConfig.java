package org.ece.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;


@Configuration
public class DatabaseConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String databaseDriver;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.url}")
    private String databaseUrl;


    @Bean
    public DataSource mysqlDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(databaseDriver);
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }
}
