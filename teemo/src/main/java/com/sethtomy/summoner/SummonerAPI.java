package com.sethtomy.summoner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sethtomy.infra.PlatformHost;
import com.sethtomy.infra.RiotClient;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class SummonerAPI {

    private final String BASEPATH = "/lol/summoner/v4/summoners";

    public SummonerAPI() {}

    public CompletableFuture<SummonerDTO> getSummonerByName(String summonerName) {
        String url = PlatformHost.NA1.getUrl() + BASEPATH + "/by-name/" + summonerName;
        HttpRequest request = RiotClient.baseRequestBuilder
                .uri(URI.create(url))
                .build();
        return RiotClient
                .sendRequest(request)
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    try {
                        return RiotClient.objectMapper.readValue(body, SummonerDTO.class);
                    } catch (JsonProcessingException e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                    }
                    return null;
                });
    }

}
