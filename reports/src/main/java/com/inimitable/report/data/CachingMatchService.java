package com.inimitable.report.data;

import com.inimitable.mapper.MatchMapper;
import com.inimitable.model.Match;
import com.inimitable.model.Summoner;
import com.inimitable.report.data.cache.AbstractInMemoryCache;
import com.inimitable.report.data.cache.MatchInMemoryCache;
import com.inimitable.report.data.cache.RetrievalResult;
import com.sethtomy.match.MatchAPI;
import com.sethtomy.summoner.SummonerAPI;
import com.sethtomy.summoner.SummonerDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class CachingMatchService implements MatchService {
    private final AbstractInMemoryCache<String, Match> cache = new MatchInMemoryCache();

    private final MatchAPI api;
    private final MatchMapper matchMapper;
    //TODO: Create a SummonerService that handles management of this data
    private final SummonerAPI summonerAPI;

    public CachingMatchService(MatchAPI api, MatchMapper matchMapper, SummonerAPI summonerAPI) {
        this.api = api;
        this.matchMapper = matchMapper;
        this.summonerAPI = summonerAPI;
    }

    @Override
    public Collection<String> getMatchHistory(Summoner summoner) {
        // TODO: SummonerService should look these up
        CompletableFuture<SummonerDTO> summonerByName = summonerAPI.getSummonerByName(summoner.getName());
        try {
            SummonerDTO summonerDTO = summonerByName.get();
            return api.getMatchHistory(summonerDTO);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Match get(String matchId) {
        return get(Collections.singletonList(matchId))
                .toStream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Flux<Match> get(Collection<String> matchIds) {
        if (matchIds == null || matchIds.size() == 0) {
            return Flux.empty();
        }

        RetrievalResult<String, Match> retrievalResult = cache.get(matchIds);
        try {
            return Flux.concat(
                    retrievalResult.getHits(),
                    retrievalResult.getMisses()
                            .map(api::getMatchById)
                            .flatMap(Mono::fromFuture)
                            .map(matchMapper::fromDTO)
                            .doOnNext(cache::put)
            );
        } catch (Exception exception) {
            log.warn("Error encountered while fetching matchIds {}", String.join(",", matchIds), exception);
        }

        return Flux.empty();
    }
}
