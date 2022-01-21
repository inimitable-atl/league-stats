package com.sethtomy.match.dto;

public record InfoDTO(
        long gameCreation,
        long gameDuration,
        long gameEndTimestamp,
        String gameId,
        String gameMode,
        String gameName,
        long gameStartTimestamp,
        String gameType,
        String gameVersion,
        int mapId,
        ParticipantDTO[] participants,
        String platformId,
        int queueId,
        TeamDTO[] teams,
        String tournamentCode
) {
}
