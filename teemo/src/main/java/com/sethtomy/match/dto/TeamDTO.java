package com.sethtomy.match.dto;

public record TeamDTO(
        BanDTO[] bans,
        ObjectivesDTO objectives,
        int teamId,
        boolean win
) {
}
