package com.inimitable.teemo.config;

import com.sethtomy.match.MatchAPI;
import com.sethtomy.summoner.SummonerAPI;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("teemo")
@Data
public class TeemoAutoConfiguration {
    private String apiKey;

    @Bean
    public MatchAPI matchService() {
        return new MatchAPI();
    }

    @Bean
    public SummonerAPI summonerService() {
        return new SummonerAPI();
    }
}
