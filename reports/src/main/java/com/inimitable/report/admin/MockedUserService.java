package com.inimitable.report.admin;

import com.inimitable.mapper.SummonerMapper;
import com.inimitable.model.Region;
import com.inimitable.model.Summoner;
import com.inimitable.model.SummonerGroup;
import com.sethtomy.summoner.SummonerAPI;
import com.sethtomy.summoner.SummonerDTO;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

@Service
public class MockedUserService implements UserService {
    public MockedUserService(SummonerAPI summonerAPI, SummonerMapper summonerMapper) {
        this.summonerAPI = summonerAPI;
        this.summonerMapper = summonerMapper;
    }

    private final SummonerAPI summonerAPI;
    private final SummonerMapper summonerMapper;

    @Override
    public Flux<Summoner> getSummonersInGroup(String username, UUID summonerGroupId) {
            CompletableFuture<SummonerDTO> summonerDTO = summonerAPI.getSummonerByName("HeavensVanguard");
            return Mono.fromFuture(summonerDTO)
                            .flatMapMany(summonerDTO1 -> Mono.just(summonerMapper.fromDTO(summonerDTO1)));
    }
}
