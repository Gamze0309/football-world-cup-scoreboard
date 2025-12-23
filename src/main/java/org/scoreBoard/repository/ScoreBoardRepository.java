package org.scoreBoard.repository;

import java.util.List;

import org.scoreBoard.model.Match;

public interface ScoreBoardRepository {
    void startMatch(String homeTeam, String awayTeam);
    List<Match> getAllMatches();
    boolean updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore);
    boolean finishMatch(String homeTeam, String awayTeam);
}
