//package foo;
//
//import org.joda.time.DateTime;
//import org.joda.time.Duration;
//
//import java.util.*;
//
//public class UserReport extends ReportGenerator {
//
//    final private RiotUser riotUser;
//
//    final private List<BarReport> barReports = new ArrayList<>();
//
//    final private Map<String, ChampionReport> championReportMap = new HashMap<>();
//
//    public UserReport(RiotUser riotUser) {
//        super();
//        this.riotUser = riotUser;
//    }
//
//    @Override
//    public void generateReport(DateTime startTime, DateTime endTime) {
//        System.out.printf("Aggregating Report for %s...%n", riotUser.name);
//        addPerSummoner(startTime, endTime);
//        System.out.printf("Aggregated Report for %s.%n", riotUser.name);
//    }
//
//    private void addPerSummoner(DateTime startTime, DateTime endTime) {
//        for (RiotSummoner summoner : riotUser.summoners) {
//            summoner.getMatchHistory(startTime, endTime);
//            var summonerReport = new BarReport(summoner);
//            barReports.add(summonerReport);
//            summonerReport.generateReport(startTime, endTime);
//            setNumMatches(getNumMatches() + summonerReport.getNumMatches());
//            setWins(getWins() + summonerReport.getWins());
//            setTimePlayed(getTimePlayed().plus(summonerReport.getTimePlayed()));
//            addPerChampion(summonerReport);
//        }
//    }
//
//    private void addPerChampion(BarReport barReport) {
//        for (ChampionReport championReport : barReport.getChampionReportMap().values()) {
//            String championName = championReport.getChampion().getName();
//            if (championReportMap.containsKey(championName)) {
//                int totalWins = championReportMap.get(championName).getWins() + championReport.getWins();
//                int totalMatches = championReportMap.get(championName).getNumMatches() + championReport.getNumMatches();
//                championReportMap.get(championName).setNumMatches(totalMatches);
//                championReportMap.get(championName).setWins(totalWins);
//            } else {
//                championReportMap.put(championName, championReport);
//            }
//        }
//    }
//
//    private static void sortByWinPercentage(ArrayList<Report> reports) {
//        var winPercentageComparator = Comparator.comparing(Report::getWinPercentage);
//        reports.sort(winPercentageComparator);
//        Collections.reverse(reports);
//    }
//
//    private String getTimePlayedString() {
//        var days = Duration.standardDays(getTimePlayed().getStandardDays());
//        var hours = Duration.standardHours(getTimePlayed().minus(days).getStandardHours());
//        var minutes = Duration.standardMinutes(getTimePlayed().minus(days).minus(hours).getStandardMinutes());
//        var seconds = Duration.standardSeconds(getTimePlayed().minus(days).minus(hours).minus(minutes).getStandardSeconds());
//
//        var stringBuilder = new StringBuilder();
//        if (days.getStandardDays() > 0) stringBuilder.append(String.format("%s days, ", days.getStandardDays()));
//        if (hours.getStandardHours() > 0) stringBuilder.append(String.format("%s hours, ", hours.getStandardHours()));
//        if (minutes.getStandardMinutes() > 0) stringBuilder.append(String.format("%s minutes, ", minutes.getStandardMinutes()));
//        if (seconds.getStandardSeconds() > 0) stringBuilder.append(String.format("%s seconds, ", seconds.getStandardSeconds()));
//        return stringBuilder.toString();
//    }
//
//    @Override
//    public String toString() {
//        var stringBuilder = new StringBuilder();
//        var str = "UserReport{" +
//                "user='" + riotUser.name + '\'' +
//                ", timePlayed='" + getTimePlayedString() + '\'' +
//                ", winPercentage='" + getWinPercentageString() + '\'' +
//                ", wins=" + getWins() +
//                ", numMatches=" + getNumMatches() +
//                "}\n";
//        stringBuilder.append(str);
//        var sorted = new ArrayList<Report>(championReportMap.values());
//        sortByWinPercentage(sorted);
//        for (Report championReport : sorted) {
//            stringBuilder.append(championReport);
//            stringBuilder.append("\n");
//        }
//        sorted = new ArrayList<>(barReports);
//        sortByWinPercentage(sorted);
//        for (Report summonerReport : sorted) {
//            stringBuilder.append(summonerReport);
//            stringBuilder.append("\n");
//        }
//        stringBuilder.append("\n");
//        return stringBuilder.toString();
//    }
//
//}
