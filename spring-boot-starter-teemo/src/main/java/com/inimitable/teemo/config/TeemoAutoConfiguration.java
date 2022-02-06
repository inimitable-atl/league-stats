package com.inimitable.teemo.config;

import com.sethtomy.infra.RiotClient;
import com.sethtomy.match.MatchAPI;
import com.sethtomy.summoner.SummonerAPI;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TeemoAutoConfiguration {

    @Bean
    public TeemoConfiguration configuration() {
        return new TeemoConfiguration();
    }

    @Bean
    @ConditionalOnMissingBean
    public RiotClient riotClient() {
        return new RiotClient(configuration().getApiKey());
    }

    @Bean
    @ConditionalOnMissingBean
    public MatchAPI matchService(RiotClient riotClient) {
        return new MatchAPI(riotClient);
    }

    @Bean
    @ConditionalOnMissingBean
    public SummonerAPI summonerService(RiotClient riotClient) {
        return new SummonerAPI(riotClient);
    }
}
