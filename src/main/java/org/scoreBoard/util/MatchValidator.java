package org.scoreBoard.util;

import java.util.List;

import org.scoreBoard.model.Match;

public class MatchValidator {
    public static void validateTeamHasNoActiveMatch(String homeTeam, String awayTeam, List<Match> activeMatches) {

        if (activeMatches == null || activeMatches.isEmpty()) return;

        for (Match match : activeMatches) {
            if (homeTeam.equalsIgnoreCase(match.getHomeTeam()) || homeTeam.equalsIgnoreCase(match.getAwayTeam())) {
                throw new IllegalStateException("Team " + homeTeam  + " already has an active match");
            }

            if (awayTeam.equalsIgnoreCase(match.getHomeTeam()) || awayTeam.equalsIgnoreCase(match.getAwayTeam())) {
                throw new IllegalStateException("Team " + awayTeam  + " already has an active match");
            }
        }
    }
}
