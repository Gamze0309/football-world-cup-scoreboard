package org.scoreBoard.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreBoard.model.Match;

public class ScoreBoardServiceTest {

    private ScoreBoardService scoreBoardService;

    @BeforeEach
    void setUp() {
        scoreBoardService = new ScoreBoardService();
    }

    @Test
    void shouldInstantiateScoreBoardService() {
        assertNotNull(scoreBoardService, "ScoreBoardService instance should have been created");
    }

    @Test
    void shouldReturnEmptyMatchesListInitially() {
        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(0, matches.size(), "Initial matches list should be empty");
    }

    @Test
    void shouldAddMatchWhenStartMatchCalled() {
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        assertEquals(1, matches.size(), "Matches list should contain one match after addition");
    }

    @Test
    void shouldStartMatchWithCorrectTeamNames() {
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatches();
        Match match = matches.get(0);

        assertEquals("Brazil", match.getHomeTeam(), "Home team name should be 'Brazil'");
        assertEquals("Argentina", match.getAwayTeam(), "Away team name should be 'Argentina'");
    }

    @Test
    void shouldRejectNullHomeTeamName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch(null, "Canada"));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyHomeTeamName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("", "Canada"));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectNullAwayTeamName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", null));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectEmptyAwayTeamName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", ""));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectSameTeamNames() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", "Canada"));
        assertEquals("Team names cannot be same", exception.getMessage());
    }

    @Test
    void shouldRejectSameTeamNamesIgnoringCase() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", "canada"));
        assertEquals("Team names cannot be same", exception.getMessage());
    }

    @Test
    void shouldRejectIfHomeTeamHasActiveMatch() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        
         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));

        assertEquals("Team Brazil already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectIfThereIsSameMatch() {
        scoreBoardService.startMatch("Brazil", "Canada");
        
         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));

        assertEquals("Team Brazil already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectStartWhenHomeTeamAlreadyPlaying() {
        scoreBoardService.startMatch("Turkey", "Canada");
        
         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Brazil", "Turkey"));

        assertEquals("Team Turkey already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectStartWhenAwayTeamHasActiveMatch() {
        scoreBoardService.startMatch("Turkey", "Canada");
        
         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", "Brazil"));

        assertEquals("Team Canada already has an active match", exception.getMessage());
    }
}
