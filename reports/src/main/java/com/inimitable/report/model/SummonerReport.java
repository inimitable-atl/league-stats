package com.inimitable.report.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
public class SummonerReport extends Report {
    private final double winRate;
    private final int wins;
    private final int losses;
    private final long timePlayedSeconds;
}
