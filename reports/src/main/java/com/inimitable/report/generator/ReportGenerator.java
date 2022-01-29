package com.inimitable.report.generator;

import com.inimitable.report.ReportRequest;
import com.inimitable.report.ReportResult;
import com.inimitable.report.model.Report;

public interface ReportGenerator<T extends Report> {
    ReportResult<T> generateReport(ReportRequest<T> request);
}