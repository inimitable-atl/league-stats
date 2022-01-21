package com.sethtomy.match.dto;

public record PerkStyleDTO(
        String description,
        PerkStyleSelectionDTO[] selections,
        int style
) {
}
