package org.scoreBoard.model;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private final int homeScore;
    private final int awayScore;

    public Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, 0, 0);
    }

    private Match(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    public Match updateScore(int homeScore, int awayScore) {
        return new Match(homeTeam, awayTeam, homeScore, awayScore);
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }
}
