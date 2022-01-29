package com.inimitable.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(toBuilder = true)
public class Summoner {
    private final Region region;
    private final String accountId;
    private final String id;
    private final String puuid;
    private final String name;
}
