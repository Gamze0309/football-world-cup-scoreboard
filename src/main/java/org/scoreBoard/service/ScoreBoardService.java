package org.scoreBoard.service;

import java.util.ArrayList;
import java.util.List;

import org.scoreBoard.model.Match;

public class ScoreBoardService {
    List<Match> matches = new ArrayList<>();
    
    public List<Match> getAllMatches() {
        return matches;
    }

    public void startMatch(String homeTeam, String awayTeam) {
        if (homeTeam == null || homeTeam.isEmpty() || awayTeam == null || awayTeam.isEmpty()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }

        if (homeTeam.equalsIgnoreCase(awayTeam)) {
            throw new IllegalArgumentException("Team names cannot be same");
        }

        for (Match match : matches) {
            if (homeTeam.equalsIgnoreCase(match.getHomeTeam()) || homeTeam.equalsIgnoreCase(match.getAwayTeam())) {
                throw new IllegalArgumentException("Team " + homeTeam  + " already has an active match");
            }

            if (awayTeam.equalsIgnoreCase(match.getHomeTeam()) || awayTeam.equalsIgnoreCase(match.getAwayTeam())) {
                throw new IllegalArgumentException("Team " + awayTeam  + " already has an active match");
            }
        }

        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        if (homeScore < 0 || awayScore < 0) {
            throw new IllegalArgumentException("Scores cannot be negative");
        }
        
        for (int i = 0; i < matches.size(); i++) {
            if (homeTeam.equalsIgnoreCase(matches.get(i).getHomeTeam()) &&
                awayTeam.equalsIgnoreCase(matches.get(i).getAwayTeam())) {
                    matches.set(i, new Match(homeTeam, awayTeam, homeScore, awayScore));
                return;
            }
        }

        throw new IllegalStateException("Match between " + homeTeam + " and " + awayTeam + " not found");
    }
}
