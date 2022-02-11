package com.sethtomy.match;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.sethtomy.infra.RegionHost;
import com.sethtomy.infra.RiotClient;
import com.sethtomy.match.dto.MatchDTO;
import com.sethtomy.summoner.SummonerDTO;
import com.sethtomy.util.HttpUtil;
import com.sethtomy.util.QueryParams;
import com.sethtomy.util.TimeUtil;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MatchAPI {

    private final int DEFAULT_COUNT = 100;
    private final String BASEPATH = "/lol/match/v5/matches";
    private final RiotClient riotClient;

    public MatchAPI(RiotClient riotClient) {
        this.riotClient = riotClient;
    }

    private Map<String, String> getMatchHistoryQueryParams(ChronoUnit timeUnit, int amount) {
        var now = Instant.now();
        // TODO: Currently using a hard-coded week, add support for user input of this
        var then = now.minus(amount, timeUnit);
        var params = new HashMap<String, String>();
        params.put(QueryParams.COUNT, String.valueOf(DEFAULT_COUNT));
        params.put(QueryParams.START_INDEX, "0");
        params.put(QueryParams.START_TIME, String.valueOf(TimeUtil.instantToEpochSeconds(then)));
        params.put(QueryParams.END_TIME, String.valueOf(TimeUtil.instantToEpochSeconds(now)));
        return params;
    }

    private void incrementStartIndexQueryParam(Map<String, String> params) {
        int startIndex = Integer.parseInt(params.get(QueryParams.START_INDEX));
        startIndex += DEFAULT_COUNT;
        params.put(QueryParams.START_INDEX, String.valueOf(startIndex));
    }

    private List<String> getMatchHistory(
            String url,
            Map<String, String> params
    ) throws IOException, InterruptedException {
        String paramUrl = HttpUtil.addQueryParams(url, params);
        HttpRequest request = riotClient.baseRequestBuilder
                .uri(URI.create(paramUrl))
                .build();
        return RiotClient.objectMapper.readValue(
                riotClient.sendRequestSync(request).body(),
                new TypeReference<>(){}
        );
    }

    private void addAdditionalMatches(
            List<String> matches,
            String url, Map<String,
            String> params
    ) throws IOException, InterruptedException {
        int responseCount = matches.size();
        while (responseCount == DEFAULT_COUNT) {
            incrementStartIndexQueryParam(params);
            var additionalMatches = getMatchHistory(url, params);
            responseCount = additionalMatches.size();
            matches.addAll(additionalMatches);
        }
    }

    /**
     * @return Array of Match Ids
     */
    public List<String> getMatchHistory(
            SummonerDTO summonerDTO,
            ChronoUnit timeUnit,
            int amount
    ) throws IOException, InterruptedException {
        String url = RegionHost.AMERICAS.getUrl() + BASEPATH + "/by-puuid/" + summonerDTO.puuid() + "/ids";
        var params = getMatchHistoryQueryParams(timeUnit, amount);
        var matches = getMatchHistory(url, params);
        addAdditionalMatches(matches, url, params);
        return matches;
    }

    public CompletableFuture<MatchDTO> getMatchById(String matchId) {
        String url = RegionHost.AMERICAS.getUrl() + BASEPATH + "/" + matchId;
        HttpRequest request = riotClient.baseRequestBuilder
                .uri(URI.create(url))
                .build();
        return riotClient.sendRequest(request)
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
