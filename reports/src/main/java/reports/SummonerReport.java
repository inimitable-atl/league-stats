package reports;

import com.sethtomy.match.MatchService;
import com.sethtomy.match.dto.MatchDTO;
import com.sethtomy.match.dto.ParticipantDTO;
import com.sethtomy.summoner.SummonerDTO;
import com.sethtomy.summoner.SummonerService;
import util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SummonerReport {

    private final SummonerDTO summonerDTO;

    private Optional<List<MatchDTO>> matchHistory = Optional.empty();

    private final SummonerService summonerService = new SummonerService();

    private final MatchService matchService = new MatchService();

    public SummonerReport(String name) throws ExecutionException, InterruptedException {
        this.summonerDTO = summonerService
                .getSummonerByName(name)
                .get();
    }

    private List<String> getMatchIds() throws ExecutionException, InterruptedException {
        return Arrays.asList(matchService
                .getMatchHistory(summonerDTO)
                .get());
    }

    // TODO: Perhaps return this async?
    public void loadMatchHistory() throws ExecutionException, InterruptedException {
        List<CompletableFuture<MatchDTO>> matchFutures = new ArrayList<>();
        for (String matchId : getMatchIds()) {
            CompletableFuture<MatchDTO> matchFuture = matchService
                    .getMatchById(matchId)
                    .thenApply((MatchDTO matchDTO) -> {
                        System.out.println(matchDTO);
                        return matchDTO;
                    });
            matchFutures.add(matchFuture);
        }
        matchHistory = Optional.of(Utils.sequence(matchFutures).get());
    }

    public double getWinRate() {
        int wins = 0;
        int totalGames = 0;
        if (matchHistory.isEmpty()) {
            throw new NullPointerException("Match history must be loaded before stats can be run.");
        }
        for (MatchDTO matchDTO : matchHistory.get()) {
            ParticipantDTO matchingParticipant = Arrays.stream(matchDTO.info().participants())
                    .filter(participantDTO -> participantDTO.puuid().equals(summonerDTO.puuid()))
                    .findFirst()
                    .get(); // We know Summoner was a participant.
            if(matchingParticipant.win()) {
                wins++;
            }
            totalGames++;
        }
        return wins / (double) totalGames;
    }

}
