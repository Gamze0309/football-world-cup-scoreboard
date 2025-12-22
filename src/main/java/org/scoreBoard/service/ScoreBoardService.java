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
        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }
}
