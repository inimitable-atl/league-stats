import reports.SummonerReport;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SummonerReport summonerReport = new SummonerReport("HeavensVanguard");
        summonerReport.loadMatchHistory();
        System.out.println(summonerReport.getWinRate());
    }

}
