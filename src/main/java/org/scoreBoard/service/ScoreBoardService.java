package org.scoreBoard.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.scoreBoard.model.Match;
import org.scoreBoard.repository.ScoreBoardRepository;
import org.scoreBoard.util.MatchValidator;
import org.scoreBoard.util.ScoreValidator;
import org.scoreBoard.util.TeamNameValidator;

public class ScoreBoardService {
    
    private final ScoreBoardRepository scoreBoardRepository;

    public ScoreBoardService(ScoreBoardRepository scoreBoardRepository) {
        this.scoreBoardRepository = scoreBoardRepository;
    }
    
    public List<Match> getAllMatchesSummary() {
        List<Match> matches = new ArrayList<>(scoreBoardRepository.getAllMatches());

        matches.sort((m1, m2) -> {
            int totalScore1 = m1.getHomeScore() + m1.getAwayScore();
            int totalScore2 = m2.getHomeScore() + m2.getAwayScore();
            return Integer.compare(totalScore2, totalScore1);
        });

        return Collections.unmodifiableList(matches);
    }

    public void startMatch(String homeTeam, String awayTeam) {
        TeamNameValidator.validateTeamName(homeTeam, awayTeam);

        List<Match> activeMatches = scoreBoardRepository.getAllMatches();
        MatchValidator.validateTeamHasNoActiveMatch(homeTeam, awayTeam, activeMatches);

        scoreBoardRepository.startMatch(homeTeam, awayTeam);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        ScoreValidator.validateScores(homeScore, awayScore);
        
        boolean updated = scoreBoardRepository.updateScore(homeTeam, awayTeam, homeScore, awayScore);
        if (!updated) {
            throw new IllegalStateException("Match between " + homeTeam + " and " + awayTeam + " not found");
        }
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        boolean finished = scoreBoardRepository.finishMatch(homeTeam, awayTeam);
        if (!finished) {
            throw new IllegalStateException("Match between " + homeTeam + " and " + awayTeam + " not found");
        }
    }
}
