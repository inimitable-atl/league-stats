package com.inimitable.input;

import lombok.Getter;

import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Getter
public class Directives {

    private final String command;

    private final String reportContext;

    private int amount;

    private final ChronoUnit timeUnit;

    public Directives(String messageContent, String nickName) {
        String[] directives = messageContent.split(" ");
        command = getCommand(directives);
        reportContext = getReportContext(directives, nickName);
        amount = getAmount(directives);
        timeUnit = getTimeUnit(directives);
    }

    private String getCommand(String[] directives) {
        if (directives.length == 1 || !directives[1].trim().toLowerCase(Locale.ROOT).equals("report")) {
            throw new IllegalArgumentException("You must include a command: e.g: 'league-stats report me 1 week");
        }
        return directives[1];
    }

    private String getReportContext(String[] directives, String nickName) {
        if (directives.length <= 2) {
            throw new IllegalArgumentException("You must include a nickname or 'me'");
        } else if (directives[2].equalsIgnoreCase("me")) {
            return nickName;
        } else {
            return directives[2];
        }
    }

    private int getAmount(String[] directives) {
        if (directives.length <= 3) {
            throw new IllegalArgumentException("You must include an amount: e.g 7");
        }
        return Integer.parseInt(directives[3]);
    }

    private ChronoUnit getTimeUnit(String[] directives) {
        if (directives.length <= 4) {
            throw new IllegalArgumentException("You must include a time unit: e.g. days");
        } else {
            ChronoUnit timeUnit;
            switch (directives[4]) {
                case "days" -> {
                    timeUnit = ChronoUnit.DAYS;
                }
                case "weeks" -> {
                    amount = amount * 7;
                    timeUnit = ChronoUnit.DAYS;
                }
                default -> throw new IllegalArgumentException("You must pick 'days' or 'weeks'");
            }
            return timeUnit;
        }
    }

}
