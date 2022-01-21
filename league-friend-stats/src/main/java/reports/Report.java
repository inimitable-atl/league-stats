package reports;

import org.joda.time.Duration;

public class Report {

    private int wins = 0;

    private int numMatches = 0;

    private Duration timePlayed = new Duration(0);

    public Report() {}

    public Duration getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(Duration timePlayed) {
        this.timePlayed = timePlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public void incrementWins() {
        wins++;
    }

    public int getNumMatches() {
        return numMatches;
    }

    public void setNumMatches(int numMatches) {
        this.numMatches = numMatches;
    }

    public void incrementNumMatches() {
        numMatches++;
    }

    public double getWinPercentage() {
        if (numMatches == 0) {
            return 0.0;
        }
        return (this.wins * 1.0) / this.numMatches;
    }

    public String getWinPercentageString() {
        return String.format("%.0f%%", (getWinPercentage() * 100));
    }

    @Override
    public String toString() {
        return "Report{" +
                ", winPercentage='" + getWinPercentageString() + '\'' +
                ", wins=" + wins +
                ", numMatches=" + numMatches +
                '}';
    }

}
