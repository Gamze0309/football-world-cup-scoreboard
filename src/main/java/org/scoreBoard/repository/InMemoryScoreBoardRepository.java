package org.scoreBoard.repository;

import java.util.ArrayList;
import java.util.List;

import org.scoreBoard.model.Match;

public class InMemoryScoreBoardRepository implements ScoreBoardRepository {
    List<Match> matches = new ArrayList<>();

    @Override
    public List<Match> getAllMatches() {
        return List.copyOf(matches);
    }

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        Match match = new Match(homeTeam, awayTeam);
        matches.add(match);
    }

    @Override
    public boolean updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        for (int i = 0; i < matches.size(); i++) {
            if (homeTeam.equalsIgnoreCase(matches.get(i).getHomeTeam()) &&
                awayTeam.equalsIgnoreCase(matches.get(i).getAwayTeam())) {
                    matches.set(i, new Match(homeTeam, awayTeam, homeScore, awayScore));
                    return true;
            }
        }
        return false;
    }

    @Override
    public boolean finishMatch(String homeTeam, String awayTeam) {
        for (int i = 0; i < matches.size(); i++) {
            if (homeTeam.equalsIgnoreCase(matches.get(i).getHomeTeam()) &&
                awayTeam.equalsIgnoreCase(matches.get(i).getAwayTeam())) {
                    matches.remove(i);
                    return true;
            }
        }
        return false;
    }
}