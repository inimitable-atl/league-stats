package com.inimitable.report.filter;

import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Builder(toBuilder = true)
@Getter
public class ReportContext {
    private final String requester;
    private final UUID summonerGroupId;
    private final List<ReportFilter> filters;
}
