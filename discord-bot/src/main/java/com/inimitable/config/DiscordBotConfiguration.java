package com.inimitable.config;

import com.inimitable.input.Directives;
import com.inimitable.report.ReportRequest;
import com.inimitable.report.ReportResult;
import com.inimitable.report.filter.ReportContext;
import com.inimitable.report.generator.SummonerReportGenerator;
import com.inimitable.report.model.SummonerReport;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.rest.util.Color;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@Configuration
public class DiscordBotConfiguration {
    private static final NumberFormat DEFAULT_PERCENTAGE_FORMAT = NumberFormat.getPercentInstance();
    private static final NumberFormat DEFAULT_DOUBLE_FORMAT = new DecimalFormat("00.##");
    private final SummonerReportGenerator summonerReportGenerator;

    public DiscordBotConfiguration(SummonerReportGenerator summonerReportGenerator) {
        this.summonerReportGenerator = summonerReportGenerator;
    }

    @Bean
    @ConditionalOnMissingBean
    public DiscordClient discordClient() {
        DiscordClient client = DiscordClient.create(discordProperties().getKey());
        Mono<Void> login = client.withGateway((GatewayDiscordClient gateway) -> {
            // ReadyEvent example
            Mono<Void> printOnLogin = gateway.on(ReadyEvent.class, event ->
                            Mono.fromRunnable(() -> {
                                final User self = event.getSelf();
                                System.out.printf("Logged in as %s#%s%n", self.getUsername(), self.getDiscriminator());
                            }))
                    .then();

            // MessageCreateEvent example
            Mono<Void> handlePingCommand = gateway.on(MessageCreateEvent.class, event -> {
                Message message = event.getMessage();
                String nickName = getNickName(event);
                if (message.getContent().contains("!league-stats")) {
                    Directives directives = new Directives(message.getContent(), nickName);
                    try {
                        ReportResult<SummonerReport> reportResult = summonerReportGenerator.generateReport(
                                ReportRequest.<SummonerReport>builder()
                                        .reportContext(
                                                ReportContext.builder()
                                                        .requester(directives.getReportContext())
                                                        .summonerGroupId(UUID.randomUUID())
                                                        .timeUnit(directives.getTimeUnit())
                                                        .amount(directives.getAmount())
                                                        .build()
                                        )
                                        .build()
                        );

                        SummonerReport report = reportResult.getReport();
                        String duration = directives.getAmount() + " " + directives.getTimeUnit().toString();
                        EmbedCreateSpec embedCreateSpec = EmbedCreateSpec.builder()
                                .title("Report results")
                                .description(String.format("User report for %s.", report.getOwner()))
                                .color(Color.GREEN)
                                .addField("Summoners", Arrays.toString(report.getSummonerNames().toArray()), false)
                                .addField("Duration", duration, false)
                                .addField("Win Rate", DEFAULT_PERCENTAGE_FORMAT.format(report.getWinRate()), false)
                                .addField("Wins", String.valueOf(report.getWins()), false)
                                .addField("Losses", String.valueOf(report.getLosses()), false)
                                .addField("Average Kill Participation", DEFAULT_PERCENTAGE_FORMAT.format(report.getAvgKillParticipation()), false)
                                .addField("Average Vision Score", DEFAULT_DOUBLE_FORMAT.format(report.getAvgVisionScore()), false)
                                .build();

                        return message.getChannel().flatMap(channel -> channel.createMessage(embedCreateSpec));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return message
                            .getChannel()
                            .flatMap(channel -> channel.createMessage("An error has occurred. Please report to an admin."));
                }

                return Mono.empty();
            }).then();

            // combine them!
            return printOnLogin
                    .and(handlePingCommand);
        });

        login.block();
        return client;
    }

    @Bean
    public DiscordProperties discordProperties() {
        return new DiscordProperties();
    }

    private static String getNickName(MessageCreateEvent event) {
        Optional<Member> optional = event.getMember();
        if (optional.isPresent()) {
            var optionalNick = optional.get().getNickname();
            if (optionalNick.isPresent()) {
                return optionalNick.get();
            }
        }
        return null;
    }
}
