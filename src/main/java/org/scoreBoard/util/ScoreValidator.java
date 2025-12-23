package org.scoreBoard.util;

public class ScoreValidator {
    public static void validateScores(int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
    } 
}
