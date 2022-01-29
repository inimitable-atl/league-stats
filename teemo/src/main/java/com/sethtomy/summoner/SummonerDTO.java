package com.sethtomy.summoner;

public record SummonerDTO(
        String accountId,
        int profileIconId,
        long revisionDate,
        String name,
        String id,
        String puuid,
        Long summonerLevel
){}
