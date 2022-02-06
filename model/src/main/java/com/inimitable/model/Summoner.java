package com.inimitable.model;

import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldNameConstants;

@Getter
@Builder(toBuilder = true)
@FieldNameConstants
public class Summoner {
    private final Region region;
    private final String accountId;
    private final String id;
    private final String puuid;
    private final String name;
}
