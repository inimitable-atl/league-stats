package com.sethtomy.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sethtomy.infra.RegionHost;
import com.sethtomy.infra.RiotClient;
import com.sethtomy.match.dto.MatchDTO;
import com.sethtomy.summoner.SummonerDTO;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class MatchService {

    private final String BASEPATH = "/lol/match/v5/matches";

    public MatchService() {
    }

    /**
     * @return Array of Match Ids
     */
    public CompletableFuture<String[]> getMatchHistory(SummonerDTO summonerDTO) {
        String url = RegionHost.AMERICAS.getUrl() + BASEPATH + "/by-puuid/" + summonerDTO.puuid() + "/ids";
        HttpRequest request = RiotClient.baseRequestBuilder
                .uri(URI.create(url))
                .build();
        return RiotClient.sendRequest(request)
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    try {
                        return RiotClient.objectMapper.readValue(body, String[].class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public CompletableFuture<MatchDTO> getMatchById(String matchId) {
        String url = RegionHost.AMERICAS.getUrl() + BASEPATH + "/" + matchId;
        HttpRequest request = RiotClient.baseRequestBuilder
                .uri(URI.create(url))
                .build();
        return RiotClient.sendRequest(request)
                .thenApply(stringHttpResponse -> {
                    System.out.println(stringHttpResponse.statusCode());
                    return stringHttpResponse;
                })
                .thenApply(HttpResponse::body)
                .thenApply(body -> {
                    try {
                        System.out.println(body);
                        return RiotClient.objectMapper.readValue(body, MatchDTO.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

}
