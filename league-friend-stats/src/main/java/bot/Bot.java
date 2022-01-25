package bot;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import reactor.core.publisher.Mono;

import reports.SummonerReport;

import java.util.Optional;

public class Bot {

    public static void main(String[] args) {
        String DISCORD_KEY = System.getenv("DISCORD_KEY");
        DiscordClient client = DiscordClient.create(DISCORD_KEY);

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
                    SummonerReport summonerReport = null;
                    try {
                        String SUMMONER = "HeavensVanguard";
                        summonerReport = new SummonerReport(SUMMONER);
                        summonerReport.loadMatchHistory(); // TODO: I'm BLOCKING!
                        sb.append(SUMMONER)
                                .append("'s win rate: ")
                                .append(summonerReport.getWinRate());
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
