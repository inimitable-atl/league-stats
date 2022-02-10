package com.inimitable.report.controller;

import com.inimitable.report.ReportRequest;
import com.inimitable.report.ReportResult;
import com.inimitable.report.filter.ReportContext;
import com.inimitable.report.generator.SummonerReportGenerator;
import com.inimitable.report.model.SummonerReport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
public class SummonerReportController {
    private final SummonerReportGenerator generator;

    public SummonerReportController(SummonerReportGenerator generator) {
        this.generator = generator;
    }

    @GetMapping("/generate")
    public ReportResult<SummonerReport> generate() throws ExecutionException, InterruptedException {
        return generator.generateReport(
                ReportRequest.<SummonerReport>builder()
                        .reportContext(ReportContext.builder().build())
                        .build()
        );
    }
}
