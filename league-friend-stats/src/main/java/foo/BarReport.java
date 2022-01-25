//package foo;
//
//import com.merakianalytics.orianna.types.core.match.Match;
//import com.merakianalytics.orianna.types.core.staticdata.Champion;
//import org.joda.time.DateTime;
//import org.joda.time.DateTimeField;
//import org.joda.time.Duration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class BarReport extends Report {
//
//    final private RiotSummoner riotSummoner;
//
//    final private Map<String, ChampionReport> championReportMap = new HashMap<>();
//
//    public BarReport(RiotSummoner summoner) {
//        super();
//        this.riotSummoner = summoner;
//    }
//
//    public void generateReport(DateTime startTime, DateTime endTime) {
//        System.out.printf("Generating report for %s...%n", riotSummoner.summoner.getName());
//        riotSummoner.getMatchHistory(startTime, endTime);
//        for (Match match : riotSummoner.matchHistory) {
//            setTimePlayed(getTimePlayed().plus(match.getDuration()));
//            Champion champion = match.getParticipants()
//                    .find(participant -> participant.getSummoner().equals(riotSummoner.summoner))
//                    .getChampion();
//            championReportMap.putIfAbsent(champion.getName(), new ChampionReport(champion));
//            if (riotSummoner.isGameWinner(match)) {
//                championReportMap.get(champion.getName()).incrementWins();
//                incrementWins();
//            }
//            championReportMap.get(champion.getName()).incrementNumMatches();
//            incrementNumMatches();
//        }
//        System.out.printf("Generated report for %s.%n", riotSummoner.summoner.getName());
//    }
//
//    public Map<String, ChampionReport> getChampionReportMap() {
//        return championReportMap;
//    }
//
//    @Override
//    public String toString() {
//        return "reports.SummonerReport{" +
//                "summoner='" + riotSummoner.summoner.getName() + '\'' +
//                ", winPercentage='" + getWinPercentageString() + '\'' +
//                ", wins=" + getWins() +
//                ", numMatches=" + getNumMatches() +
//                "}";
//    }
//
//}
