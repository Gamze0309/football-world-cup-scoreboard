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

        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }
}
