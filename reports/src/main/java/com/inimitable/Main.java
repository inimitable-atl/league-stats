package com.inimitable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        SummonerReport summonerReport = new SummonerReport("HeavensVanguard");
//        summonerReport.loadMatchHistory();
//        System.out.println(summonerReport.getWinRate());
//    }
}
