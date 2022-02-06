package com.inimitable.teemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("teemo")
public class TeemoConfiguration {
    private String apiKey;
}
