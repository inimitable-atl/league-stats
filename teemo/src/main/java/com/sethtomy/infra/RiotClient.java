package com.sethtomy.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.RateLimiter;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;

public class RiotClient {
    public static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMinutes(2))
            .build();
    public static ObjectMapper objectMapper = new ObjectMapper()
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    private static RateLimiter secondRateLimiter = RateLimiter.create(0.833333);
    public final HttpRequest.Builder baseRequestBuilder;
    private final String apiKey;

    public RiotClient(String apiKey) {
        this.apiKey = apiKey;
        baseRequestBuilder = HttpRequest.newBuilder()
                .timeout(Duration.ofMinutes(2))
                .header("X-Riot-Token", apiKey);
    }

    public CompletableFuture<HttpResponse<String>> sendRequest(HttpRequest request) {
        secondRateLimiter.acquire(1);
        System.out.printf("Sending request to %s...\n", request.uri());
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(stringHttpResponse -> {
                    System.out.printf(
                            "[%s] Successfully sent request to %s", stringHttpResponse.statusCode(), request.uri()
                    );
                    return stringHttpResponse;
                });
    }

}
