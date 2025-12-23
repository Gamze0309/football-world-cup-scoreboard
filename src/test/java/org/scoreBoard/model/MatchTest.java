package org.scoreBoard.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class MatchTest {

    @Test
    void shouldInstantiateMatch() {
        Match match = new Match("Mexico", "Canada");
        assertNotNull(match, "Match instance should have been created");
    }

    @Test
    void shouldReturnTeamNamesFromGetters() {
        Match match = new Match("Mexico", "Canada");
        assertEquals("Mexico", match.getHomeTeam());
        assertEquals("Canada", match.getAwayTeam());
    }
    
    @Test
    void shouldInitializeScoresToZero() {
        Match match = new Match("Mexico", "Canada");

        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }

    @Test
    void shouldPreserveOriginalAfterUpdateScore() {
        Match match = new Match("Mexico", "Canada");
        Match updated = match.updateScore(1, 2);

        assertEquals(1, updated.getHomeScore());
        assertEquals(2, updated.getAwayScore());

        assertEquals(0, match.getHomeScore());
        assertEquals(0, match.getAwayScore());
    }
}
