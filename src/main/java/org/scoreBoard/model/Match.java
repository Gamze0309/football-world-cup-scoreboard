package org.scoreBoard.model;

public class Match {
    private final String homeTeam;
    private final String awayTeam;
    private final int homeScore;
    private final int awayScore;
    private final long insertionOrder;

    public Match(String homeTeam, String awayTeam) {
        this(homeTeam, awayTeam, 0);
    }

    public Match(String homeTeam, String awayTeam, long insertionOrder) {
        this(homeTeam, awayTeam, 0, 0, insertionOrder);
    }

    private Match(String homeTeam, String awayTeam, int homeScore, int awayScore, long insertionOrder) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.insertionOrder = insertionOrder;
    }

    public Match updateScore(int homeScore, int awayScore) {
        return new Match(homeTeam, awayTeam, homeScore, awayScore, insertionOrder);
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

    public long getInsertionOrder() { 
        return insertionOrder; 
    }
}
