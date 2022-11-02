package com.prakash.netpricecalculator.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
@Data
public class ConfigProperties {
    private String url = "http://localhost:8080";
}