package org.scoreBoard.model;

public class Match {
    private final String homeTeam;
    private final String awayTeam;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return 0;
    }

    public int getAwayScore() {
        return 0;
    }
}
