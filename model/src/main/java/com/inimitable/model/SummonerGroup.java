package com.inimitable.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
public class SummonerGroup {
    @Builder.Default
    private final UUID groupId = UUID.randomUUID();
    @Singular
    private final List<Summoner> summoners;
}
