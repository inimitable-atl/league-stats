package com.inimitable.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("discord")
@Data
public class DiscordProperties {
    private String key;
}
