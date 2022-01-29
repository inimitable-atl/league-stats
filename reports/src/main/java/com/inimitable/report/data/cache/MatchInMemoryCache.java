package com.inimitable.report.data.cache;

import com.inimitable.model.Match;

public class MatchInMemoryCache extends AbstractInMemoryCache<String, Match> {
    @Override
    public String getKey(Match source) {
        return source.getMatchId();
    }
}
