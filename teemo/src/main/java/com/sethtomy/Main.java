package com.sethtomy;

import com.sethtomy.infra.RiotClient;
import com.sethtomy.match.MatchAPI;
import com.sethtomy.match.dto.MatchDTO;
import com.sethtomy.match.dto.ParticipantDTO;
import com.sethtomy.summoner.SummonerDTO;
import com.sethtomy.summoner.SummonerAPI;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
        RiotClient riotClient = new RiotClient(System.getenv("RIOT_TOKEN"));
        SummonerAPI summonerAPI = new SummonerAPI(riotClient);
        MatchAPI matchAPI = new MatchAPI(riotClient);
        SummonerDTO summonerDTO = summonerAPI
                .getSummonerByName("HeavensVanguard")
                .get();

        List<String> matchIds = matchAPI
                .getMatchHistory(summonerDTO, ChronoUnit.DAYS, 1);

        List<CompletableFuture<MatchDTO>> matchFutures = new ArrayList<>();
        for (String matchId : matchIds) {
            CompletableFuture<MatchDTO> matchFuture = matchAPI
                    .getMatchById(matchId)
                    .thenApply(matchDTO -> {
                        System.out.println(matchDTO);
                        return matchDTO;
                    });
            matchFutures.add(matchFuture);
        }
        List<MatchDTO> matchDTOs = sequence(matchFutures).get();
        System.out.println(getWinRate(summonerDTO, matchDTOs));
    }

    // https://nurkiewicz.com/2013/05/java-8-completablefuture-in-action.html
    private static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.<T>toList())
        );
    }

    private static double getWinRate(SummonerDTO summonerDTO, List<MatchDTO> matchDTOS) {
        int wins = 0;
        double totalGames = 0;
        for (MatchDTO matchDTO : matchDTOS) {
            ParticipantDTO matchingParticipant = Arrays.stream(matchDTO.info().participants())
                    .filter(participantDTO -> participantDTO.puuid().equals(summonerDTO.puuid()))
                    .findFirst()
                    .get();
            if(matchingParticipant.win()) {
                wins++;
            }
            totalGames++;
        }
        return wins / totalGames;
    }

}
