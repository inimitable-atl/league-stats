package com.inimitable.report.generator;

import com.inimitable.model.Match;
import com.inimitable.model.Participant;
import com.inimitable.model.Summoner;
import com.inimitable.model.SummonerGroup;
import com.inimitable.report.ReportRequest;
import com.inimitable.report.ReportResult;
import com.inimitable.report.admin.UserService;
import com.inimitable.report.data.MatchService;
import com.inimitable.report.filter.ReportContext;
import com.inimitable.report.model.SummonerReport;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.tuple.Pair;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Log4j2
public class SummonerReportGenerator implements ReportGenerator<SummonerReport> {

    private final MatchService matchService;
    private final UserService userService;

    public SummonerReportGenerator(MatchService matchService, UserService userService) {
        this.matchService = matchService;
        this.userService = userService;
    }

    @Override
    public ReportResult<SummonerReport> generateReport(ReportRequest<SummonerReport> request) {
        ReportResult.ReportResultBuilder<SummonerReport> result = ReportResult.builder();
        ReportContext context = request.getReportContext();

        Flux<Summoner> summoners = userService.getSummonersInGroup(context.getRequester(), context.getSummonerGroupId());
        Collection<Pair<Summoner, Match>> matches =
                summoners
                        .map(summoner -> Pair.of(summoner, matchService.getMatchHistory(summoner)))
                        .flatMap((Function<Pair<Summoner, Collection<String>>, Publisher<Pair<Summoner, Match>>>) pair -> {
                                    Summoner summoner = pair.getKey();
                                    return matchService.get(pair.getValue())
                                            .map(val -> Pair.of(summoner, val));
                                }
                        )
                        .toStream()
                        .toList();

        int wins = 0;
        int losses = 0;
        long timePlayedSeconds = 0;
        for (Pair<Summoner, Match> summonerMatch : matches) {
            Summoner summoner = summonerMatch.getLeft();
            Match match = summonerMatch.getRight();
            Participant primaryParticipant = match.getParticipants()
                    .stream()
                    .filter(participant -> participant.getPuuid().equals(summoner.getPuuid()))
                    .findFirst()
                    .orElse(null);
            if (primaryParticipant != null) {
                log.info("{}: {}", primaryParticipant.getSummonerName(), primaryParticipant.isWin());
                // Found participant, let's aggregate
                if (primaryParticipant.isWin()) {
                    wins++;
                } else {
                    losses++;
                }
                timePlayedSeconds += primaryParticipant.getTimePlayed();
            }
        }
        double winRate = 1.0D * wins / (wins + losses);

        SummonerReport.SummonerReportBuilder<?, ?> builder = SummonerReport.builder();
        builder.wins(wins);
        builder.losses(losses);
        builder.winRate(winRate);
        builder.timePlayedSeconds(timePlayedSeconds);

        result.success(true);
        result.report(builder.build());
        return result.build();
    }
}
