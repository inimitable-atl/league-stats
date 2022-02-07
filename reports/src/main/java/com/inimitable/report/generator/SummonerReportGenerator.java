package com.inimitable.report.generator;

import com.inimitable.model.Match;
import com.inimitable.model.Participant;
import com.inimitable.model.Summoner;
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
        int totalVisionScore = 0;
        long timePlayedSeconds = 0;
        double killParticipation = 0;
        for (Pair<Summoner, Match> summonerMatch : matches) {
            Summoner summoner = summonerMatch.getLeft();
            Match match = summonerMatch.getRight();
            Participant primaryParticipant = match.getParticipants()
                    .stream()
                    .filter(participant -> participant.getPuuid().equals(summoner.getPuuid()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("The match linked with this participant should always contain the participant"));

            if (primaryParticipant.isWin()) {
                wins++;
            } else {
                losses++;
            }

            // Inefficient to iterate twice but easier to read I suppose
            int teamKills = match.getParticipants()
                    .parallelStream()
                    .filter(participant -> participant.getTeamId() == primaryParticipant.getTeamId())
                    .mapToInt(Participant::getKills)
                    .sum();

            if (teamKills != 0) {
                killParticipation += (double) (primaryParticipant.getKills() + primaryParticipant.getAssists()) / teamKills;
            }
            timePlayedSeconds += primaryParticipant.getTimePlayed();
            totalVisionScore += primaryParticipant.getVisionScore();
        }
        double avgVisionScore = 1.0D * totalVisionScore / matches.size();
        double avgKillParticipation = killParticipation / matches.size();
        double winRate = 1.0D * wins / matches.size();

        SummonerReport.SummonerReportBuilder<?, ?> builder = SummonerReport.builder();
        builder.wins(wins);
        builder.losses(losses);
        builder.winRate(winRate);
        builder.avgKillParticipation(avgKillParticipation);
        builder.timePlayedSeconds(timePlayedSeconds);
        builder.avgVisionScore(avgVisionScore);

        result.success(true);
        result.report(builder.build());
        return result.build();
    }
}
