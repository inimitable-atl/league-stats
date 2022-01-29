package com.inimitable.config;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class DiscordBotConfiguration {
    private final SummonerReportGenerator summonerReportGenerator;

    public DiscordBotConfiguration(SummonerReportGenerator summonerReportGenerator) {
        this.summonerReportGenerator = summonerReportGenerator;
    }

    @Bean
    public DiscordProperties discordProperties() {
        return new DiscordProperties();
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
                if (message.getContent().equalsIgnoreCase("!league-stats")) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("```");
                    try {
                        ReportResult<SummonerReport> reportResult = summonerReportGenerator.generateReport(
                                ReportRequest.<SummonerReport>builder()
                                        .reportContext(
                                                ReportContext.builder()
                                                        .requester(nickName)
                                                        .summonerGroupId(UUID.randomUUID())
                                                        .build()
                                        )
                                        .build()
                        );

                        SummonerReport report = reportResult.getReport();

                        String SUMMONER = "HeavensVanguard";
                        sb.append(SUMMONER)
                                .append("'s win rate: ")
                                .append(report.getWinRate());
                    } catch (Exception e) {
                        e.printStackTrace();
                        sb.append("An error has occurred. Please report to an admin.");
                    }

//                    sb.append(Reporter.report(new String[]{"week", "1", nickName}));
                    sb.append("```");
                    message.getChannel();
                    return message
                            .getChannel()
                            .flatMap(channel -> channel.createMessage(sb.toString()));
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

    private static String getNickName(MessageCreateEvent event) {
        Optional<Member> optional = event.getMember();
        if (optional.isPresent()) {
            var optionalNick = optional.get().getNickname();
            if(optionalNick.isPresent()) {
                return optionalNick.get();
            }
        }
        return null;
    }
}
