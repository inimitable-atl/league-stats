package com.inimitable.report.data;

import com.inimitable.mapper.MatchMapper;
import com.inimitable.model.Match;
import com.inimitable.model.Summoner;
import com.inimitable.report.data.cache.AbstractInMemoryCache;
import com.inimitable.report.data.cache.MatchInMemoryCache;
import com.inimitable.report.data.cache.RetrievalResult;
import com.sethtomy.match.MatchAPI;
import com.sethtomy.match.dto.MatchDTO;
import com.sethtomy.summoner.SummonerAPI;
import com.sethtomy.summoner.SummonerDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            return api.getMatchHistory(summonerDTO).get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public Match get(String matchId) {
        return get(Collections.singletonList(matchId))
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public Collection<Match> get(Collection<String> matchIds) {
        RetrievalResult<String, Match> retrievalResult = cache.get(matchIds);
        if (retrievalResult.getHits().size() == matchIds.size()) {
            return retrievalResult.getHits();
        }

        Collection<Match> result = new ArrayList<>(retrievalResult.getHits());
        matchIds.forEach(matchId -> {
            CompletableFuture<MatchDTO> matchById = api.getMatchById(matchId);
            try {
                MatchDTO matchDTO = matchById.get();
                Match match = matchMapper.fromDTO(matchDTO);
                cache.put(match);
                result.add(match);
            } catch (Exception exception) {
                log.warn("Error encountered while fetching matchId {}", matchId, exception);
            }
        });
        return result;
    }
}
