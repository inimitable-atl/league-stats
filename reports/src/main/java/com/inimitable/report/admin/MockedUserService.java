package com.inimitable.report.admin;

import com.inimitable.mapper.SummonerMapper;
import com.inimitable.model.Summoner;
import com.sethtomy.summoner.SummonerAPI;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Stream;

@Service
public class MockedUserService implements UserService {
    public MockedUserService(SummonerAPI summonerAPI, SummonerMapper summonerMapper) {
        this.summonerAPI = summonerAPI;
        this.summonerMapper = summonerMapper;
    }

    private final SummonerAPI summonerAPI;
    private final SummonerMapper summonerMapper;

    @Override
    public Flux<Summoner> getSummonersInGroup(
            String username,
            UUID summonerGroupId
    ) throws ExecutionException, InterruptedException {
            List<Summoner> summoners = new ArrayList<>();
            // TODO: Make this async
            for (String summonerName : getDefaults(username)) {
                summoners.add(
                        summonerMapper.fromDTO(
                                summonerAPI.getSummonerByName(summonerName).get()
                        )
                );
            }
            return Flux.fromStream(summoners.stream());
    }

    private List<String> getDefaults(String username) {
        List<String> summoners = new ArrayList<>();
        switch (username) {
            case "Seth" -> summoners = List.of("HeavensVanguard");
            case "Andrew" -> summoners =  List.of("damadbagginj3w", "Creamy Vibes", "feedthenafkff15");
            case "Champagne" -> summoners = List.of("RAINchampagne");
            case "Jack" -> summoners = List.of("Mr BOjangles");
            case "Nick" -> summoners = List.of("Sommazz", "Sultry Vibes", "OctoFreshMom");
        }
        return summoners;
    }
}
