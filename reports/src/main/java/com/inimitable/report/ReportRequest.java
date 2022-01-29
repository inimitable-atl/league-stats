package com.inimitable.report;

import com.inimitable.report.filter.ReportContext;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
public class ReportRequest<T> {
    private Class<T> type;
    private ReportContext reportContext;
}
