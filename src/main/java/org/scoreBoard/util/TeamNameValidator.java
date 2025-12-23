package org.scoreBoard.util;

public class TeamNameValidator {

    public static void validateTeamName(String homeTeam, String awayTeam) {
        validateTeamNameNotNullOrEmpty(homeTeam);
        validateTeamNameNotNullOrEmpty(awayTeam);
        validateTeamsNotEqual(homeTeam, awayTeam);
    }

    private static void validateTeamNameNotNullOrEmpty(String teamName) {
        if (teamName == null || teamName.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
    }

    private static void validateTeamsNotEqual(String homeTeam, String awayTeam) {
        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be same");
        }
    }
}
