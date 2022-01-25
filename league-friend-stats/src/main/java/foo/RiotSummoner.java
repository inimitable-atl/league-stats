//package foo;
//
//import org.joda.time.DateTime;
//
//public class RiotSummoner {
//
//    Summoner summoner;
//
//    MatchHistory matchHistory;
//
//    public RiotSummoner(String summonerName) {
//        this.summoner = Summoner.named(summonerName).get();
//    }
//
//    public void getMatchHistory(DateTime startTime, DateTime endTime) {
//            this.matchHistory = summoner.matchHistory()
//                    .withStartTime(startTime)
//                    .withEndTime(endTime)
//                    .get();
//    }
//
//    public boolean isGameWinner(Match match) {
//        Team redTeam = match.getRedTeam();
//        Participant summonerMatch = redTeam
//                .getParticipants()
//                .find(participant -> participant.getSummoner().equals(this.summoner));
//        if (summonerMatch != null) {
//            return redTeam.isWinner();
//        } else {
//            return !redTeam.isWinner();
//        }
//    }
//
//}
