package org.scoreBoard.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.scoreBoard.model.Match;

public class ScoreBoardServiceTest {

    @Test
    void shouldInstantiateScoreBoardService() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        assertNotNull(scoreBoardService, "ScoreBoardService instance should have been created");
    }

    @Test
    void shouldReturnEmptyMatchesListInitially() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(0, matches.size(), "Initial matches list should be empty");
    }

    @Test
    void shouldAddMatchWhenStartMatchCalled() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(1, matches.size(), "Matches list should contain one match after addition");
    }

    @Test
    void shouldStartMatchWithCorrectTeamNames() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        Match match = matches.get(0);

        assertEquals("Brazil", match.getHomeTeam(), "Home team name should be 'Brazil'");
        assertEquals("Argentina", match.getAwayTeam(), "Away team name should be 'Argentina'");
    }

    @Test
    void shouldRejectNullHomeTeamName() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch(null, "Canada"));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyHomeTeamName() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("", "Canada"));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectNullAwayTeamName() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", null));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyAwayTeamName() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", ""));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectSameTeamNames() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", "Canada"));
        assertEquals("Team names cannot be same", exception.getMessage());
    }

    @Test
    void shouldRejectSameTeamNamesIgnoringCase() {
        ScoreBoardService scoreBoardService = new ScoreBoardService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", "canada"));
        assertEquals("Team names cannot be same", exception.getMessage());
    }
}
