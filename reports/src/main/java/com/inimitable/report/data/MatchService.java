package com.inimitable.report.data;

import com.inimitable.model.Match;
import com.inimitable.model.Summoner;
import reactor.core.publisher.Flux;

import java.time.temporal.ChronoUnit;
import java.util.Collection;

public interface MatchService {
    Collection<String> getMatchHistory(Summoner summoner, ChronoUnit timeUnit, int amount);

    Match get(String matchId);

    Flux<Match> get(Collection<String> matchIds);
}
