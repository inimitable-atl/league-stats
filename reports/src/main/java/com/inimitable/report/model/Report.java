package com.inimitable.report.model;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
public abstract class Report {
    @Builder.Default
    private final UUID uuid = UUID.randomUUID();
    private final String owner;
}
