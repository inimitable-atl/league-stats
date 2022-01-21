package com.sethtomy.match.dto;

public record MetadataDTO(
        String dataVersion,
        String matchId,
        String[] participants // PUUID's
) {
}
