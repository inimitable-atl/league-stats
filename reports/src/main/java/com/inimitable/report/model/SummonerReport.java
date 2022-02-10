package com.inimitable.report.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@SuperBuilder(toBuilder = true)
public class SummonerReport extends Report {
    private final double winRate;
    private final int wins;
    private final int losses;
    private final double avgKillParticipation;
    private final double avgVisionScore;
    private final long timePlayedSeconds;
    private final List<String> summonerNames;
}
