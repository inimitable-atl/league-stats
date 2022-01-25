//package foo;
//
//import config.YamlConfigRunner;
//import org.joda.time.DateTime;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//
//public class Reporter {
//
//    public static void main(String[] args) {
//        report(args);
//    }
//
//    public static String report(String[] args) {
//
//        // TODO: Add hours played to report
//        var config = new YamlConfigRunner();
//        var users= config.load();
//
//        if (args.length < 2) {
//            System.err.println("You must provide a interval and and interval amount as arguments");
//            System.exit(1);
//        }
//
//        if (args.length == 3) {
//            for (RiotUser user : users) {
//                if (user.name.equalsIgnoreCase(args[2])) {
//                    users = new ArrayList<RiotUser>();
//                    users.add(user);
//                }
//            }
//        }
//
//        DateTime endTime = DateTime.now();
//        int intervalAmount = getIntervalAmount(args[1]);
//        DateTime startTime = getStartTime(endTime, args[0], intervalAmount);
//
//        ArrayList<Report> reports = new ArrayList<>();
//        for (RiotUser riotUser : users) {
//            UserReport report = new UserReport(riotUser);
//            report.generateReport(startTime, endTime);
//            reports.add(report);
//        }
//
//        sortByWinPercentage(reports);
//        System.out.println();
//
//        String response = buildReports(reports);
//        System.out.println(response);
//        return response;
//    }
//
//    private static DateTime getStartTime(DateTime endTime, String interval, int intervalAmount) {
//        switch (interval) {
//            case "day":
//                return endTime.minusDays(intervalAmount);
//            case "week":
//                return endTime.minusWeeks(intervalAmount);
//            case "month":
//                return endTime.minusMonths(intervalAmount);
//            case "year":
//                return endTime.minusYears(intervalAmount);
//            default:
//                System.err.printf("'%s' is not a valid interval setting [day, week, month, year]%n", interval);
//                System.exit(1);
//                return DateTime.now(); // Still expects return
//        }
//    }
//
//    private static int getIntervalAmount(String intervalAmountString) {
//        try {
//            int intervalAmount = Integer.parseInt(intervalAmountString);
//            if (intervalAmount <= 0) {
//                System.err.println("Interval amount must be greater than 0");
//                System.exit(1);
//            }
//            return intervalAmount;
//        } catch (NumberFormatException e) {
//            System.err.printf("'%s' is not a valid interval amount setting which must be an integer%n", intervalAmountString);
//            System.exit(1);
//            return -1; // Still expects return
//        }
//    }
//
//    private static String buildReports(ArrayList<Report> reports) {
//        StringBuilder sb = new StringBuilder();
//        for (Report report : reports) {
//            sb.append(report);
//            sb.append("\n");
//        }
//        return sb.toString();
//    }
//
//    private static void sortByWinPercentage(ArrayList<Report> reports) {
//        var winPercentageComparator = Comparator.comparing(Report::getWinPercentage);
//        reports.sort(winPercentageComparator);
//        Collections.reverse(reports);
//    }
//
//}
