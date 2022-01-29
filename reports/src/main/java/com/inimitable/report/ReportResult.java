package com.inimitable.report;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Builder(toBuilder = true)
@Getter
public class ReportResult<T> {
    @Singular
    private final List<String> errors;
    private final boolean success;
    private final T report;
}
