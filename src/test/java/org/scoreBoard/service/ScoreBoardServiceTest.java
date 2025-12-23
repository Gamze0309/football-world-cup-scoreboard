package org.scoreBoard.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.scoreBoard.model.Match;
import org.scoreBoard.repository.InMemoryScoreBoardRepository;
import org.scoreBoard.repository.ScoreBoardRepository;

public class ScoreBoardServiceTest {

    private ScoreBoardService scoreBoardService;
    private ScoreBoardRepository scoreBoardRepository;

    @BeforeEach
    void setUp() {
        scoreBoardRepository = new InMemoryScoreBoardRepository();
        scoreBoardService = new ScoreBoardService(scoreBoardRepository);
    }

    @Test
    void shouldInstantiateScoreBoardService() {
        assertNotNull(scoreBoardService, "ScoreBoardService instance should have been created");
    }

    @Test
    void shouldReturnEmptyMatchesListInitially() {
        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(0, matches.size(), "Initial matches list should be empty");
    }

    @Test
    void shouldAddMatchWhenStartMatchCalled() {
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(1, matches.size(), "Matches list should contain one match after addition");
    }

    @Test
    void shouldStartMatchWithCorrectTeamNames() {
        scoreBoardService.startMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
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
    void shouldRejectWhitespaceOnlyHomeTeamName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch(" ", "Canada"));
        assertEquals("Team name cannot be null or empty", exception.getMessage());
    }

    @Test
    void shouldRejectWhitespaceOnlyAwayTeamName() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.startMatch("Canada", " "));
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
        
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));

        assertEquals("Team Brazil already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectIfThereIsSameMatch() {
        scoreBoardService.startMatch("Brazil", "Canada");
        
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));

        assertEquals("Team Brazil already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectStartWhenHomeTeamAlreadyPlaying() {
        scoreBoardService.startMatch("Turkey", "Canada");
        
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Brazil", "Turkey"));

        assertEquals("Team Turkey already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectStartWhenAwayTeamHasActiveMatch() {
        scoreBoardService.startMatch("Turkey", "Canada");
        
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Canada", "Brazil"));

        assertEquals("Team Canada already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectIfHomeTeamHasActiveMatchIgnoringCase() {
        scoreBoardService.startMatch("Turkey", "brazil");
        
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("Brazil", "Canada"));

        assertEquals("Team Brazil already has an active match", exception.getMessage());
    }

    @Test
    void shouldRejectIfAwayTeamHasActiveMatchIgnoringCase() {
        scoreBoardService.startMatch("Turkey", "Canada");
        
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.startMatch("canada", "Brazil"));

        assertEquals("Team canada already has an active match", exception.getMessage());
    }

    @Test
    void shouldUpdateMatchScores() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.updateScore("Brazil", "Argentina", 2, 3);

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(2, matches.get(0).getHomeScore(), "Home team score should be updated to 2");
        assertEquals(3, matches.get(0).getAwayScore(), "Away team score should be updated to 3");
    }

    @Test
    void shouldUpdateMultipleMatchScores() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.updateScore("Brazil", "Argentina", 2, 3);
        scoreBoardService.startMatch("Turkey", "Canada");
        scoreBoardService.updateScore("Turkey", "Canada", 1, 1);

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(2, matches.get(0).getHomeScore(), "Home team score should be updated to 2");
        assertEquals(3, matches.get(0).getAwayScore(), "Away team score should be updated to 3");
        assertEquals(1, matches.get(1).getHomeScore(), "Home team score should be updated to 1");
        assertEquals(1, matches.get(1).getAwayScore(), "Away team score should be updated to 1");
    }

    @Test
    void shouldUpdateHomeScoreIgnoringCase() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.updateScore("brazil", "Argentina", 2, 3);

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(2, matches.get(0).getHomeScore(), "Home team score should be updated to 2");
        assertEquals(3, matches.get(0).getAwayScore(), "Away team score should be updated to 3");
    }

    @Test
    void shouldUpdateAwayScoreIgnoringCase() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.updateScore("Brazil", "argentina", 2, 3);

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(2, matches.get(0).getHomeScore(), "Home team score should be updated to 2");
        assertEquals(3, matches.get(0).getAwayScore(), "Away team score should be updated to 3");
    }

    @Test
    void shouldNotUpdateNonExistingMatch() {
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.updateScore("Canada", "Brazil", 1, 1));

        assertEquals("Match between Canada and Brazil not found", exception.getMessage());
    }

    @Test
    void shouldNotUpdateWithNegativeHomeScore() {
        scoreBoardService.startMatch("Canada", "Brazil");
         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.updateScore("Canada", "Brazil", -1, 1));

        assertEquals("Scores cannot be negative", exception.getMessage());
    }

    @Test
    void shouldNotUpdateWithNegativeAwayScore() {
        scoreBoardService.startMatch("Canada", "Brazil");
         IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> scoreBoardService.updateScore("Canada", "Brazil", 1, -1));

        assertEquals("Scores cannot be negative", exception.getMessage());
    }

    @Test
    void shouldFinishMatch() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.finishMatch("Brazil", "Argentina");

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(0, matches.size(), "Matches list should be empty after finishing the match");
    }

    @Test
    void shouldFinishOneMatchAndKeepOtherActive() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.startMatch("Turkey", "Canada");
        scoreBoardService.finishMatch("Turkey", "Canada");

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(1, matches.size(), "Matches list should contain one match after finishing one match");
        assertEquals("Brazil", matches.get(0).getHomeTeam(), "Remaining match should be Brazil vs Argentina");
        assertEquals("Argentina", matches.get(0).getAwayTeam(), "Remaining match should be Brazil vs Argentina");
    }

    @Test
    void shoouldFinishMatchIgnoringCase() {
        scoreBoardService.startMatch("Brazil", "Argentina");
        scoreBoardService.finishMatch("brazil", "argentina");

        List<Match> matches = scoreBoardService.getAllMatchesSummary();
        assertEquals(0, matches.size(), "Matches list should be empty after finishing the match");
    }

    @Test
    void shouldNotFinishNonExistingMatch() {
         IllegalStateException exception = assertThrows(IllegalStateException.class,
            () -> scoreBoardService.finishMatch("Brazil", "Turkey"));

        assertEquals("Match between Brazil and Turkey not found", exception.getMessage());
    }

    @Test
    void shouldNotFinishSameMatchTwice() {
        scoreBoardService.startMatch("Mexico", "Canada");
        scoreBoardService.finishMatch("Mexico", "Canada");

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            scoreBoardService.finishMatch("Mexico", "Canada");
        });

        assertEquals("Match between Mexico and Canada not found", exception.getMessage());
    }

    @Test
    void shouldReturnUnmodifiableMatchesList() {
        List<Match> result = scoreBoardService.getAllMatchesSummary();
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class,
            () -> result.add(new Match("Brazil", "Argentina")));
    
        assertNotNull(ex, "Should throw UnsupportedOperationException when trying to modify the matches list");
    }

    @Test
    void shouldReturnMatchesSortedByTotalScoreDescending() {
        scoreBoardService.startMatch("Germany", "France");
        scoreBoardService.startMatch("Mexico", "Canada");
        scoreBoardService.startMatch("Spain", "Brazil");

        scoreBoardService.updateScore("Mexico", "Canada", 0, 5);
        scoreBoardService.updateScore("Spain", "Brazil", 10, 2);
        scoreBoardService.updateScore("Germany", "France", 2, 2);

        List<Match> summary = scoreBoardService.getAllMatchesSummary();
        assertEquals("Spain", summary.get(0).getHomeTeam());
        assertEquals("Mexico", summary.get(1).getHomeTeam());
        assertEquals("Germany", summary.get(2).getHomeTeam());
    }

    @Test
    void shouldGetSortedSummaryByTotalScore() {
        scoreBoardService.startMatch("Mexico", "Canada");
        scoreBoardService.startMatch("Spain", "Brazil");
        scoreBoardService.startMatch("Germany", "France");
        
        scoreBoardService.updateScore("Mexico", "Canada", 2, 5);
        scoreBoardService.updateScore("Spain", "Brazil", 10, 3);
        scoreBoardService.updateScore("Germany", "France", 2, 4);

        List<Match> summary = scoreBoardService.getAllMatchesSummary();
        assertEquals("Spain", summary.get(0).getHomeTeam());
        assertEquals("Mexico", summary.get(1).getHomeTeam());
        assertEquals("Germany", summary.get(2).getHomeTeam());
    }

    @Test
    void shouldPreserveInsertionOrderAfterUpdate() {
        scoreBoardService.startMatch("Mexico", "Canada");
        scoreBoardService.startMatch("Spain", "Brazil");

        List<Match> beforeUpdateSummary = scoreBoardService.getAllMatchesSummary();

        Match mexicoCanadaBefore = beforeUpdateSummary.stream()
        .filter(m -> m.getHomeTeam().equals("Mexico") && m.getAwayTeam().equals("Canada"))
        .findFirst()
        .orElseThrow();

        long insertionOrderBefore = mexicoCanadaBefore.getInsertionOrder();

        scoreBoardService.updateScore("Mexico", "Canada", 2, 2);
        scoreBoardService.updateScore("Spain", "Brazil", 1, 5);

        List<Match> afterUpdateSummary = scoreBoardService.getAllMatchesSummary();
        Match mexicoCanadaAfter = afterUpdateSummary.stream()
        .filter(m -> m.getHomeTeam().equals("Mexico") && m.getAwayTeam().equals("Canada"))
        .findFirst()
        .orElseThrow();

        assertEquals(insertionOrderBefore, mexicoCanadaAfter.getInsertionOrder(), "InsertionOrder must not change after score updates");
    }
}
