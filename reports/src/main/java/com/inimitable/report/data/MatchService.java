package com.inimitable.report.data;

import com.inimitable.model.Match;
import com.inimitable.model.Summoner;

import java.util.Collection;

public interface MatchService {
    Collection<String> getMatchHistory(Summoner summoner);

    Match get(String matchId);

    Collection<Match> get(Collection<String> matchIds);
}
