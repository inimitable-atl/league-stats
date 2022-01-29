package com.inimitable.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Match {
    @EqualsAndHashCode.Include
    private final String matchId;

    long gameCreation;
    long gameDuration;
    long gameEndTimestamp;
    String gameId;
    String gameMode;
    String gameName;
    long gameStartTimestamp;
    String gameType;
    String gameVersion;
    int mapId;
    Set<Participant> participants;
    String platformId;
    int queueId;
    String tournamentCod;
}
