package org.sportdatacompany.livescoreboard;

import org.assertj.core.api.ListAssert;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class LiveScoreBoardTest {

    private final LiveScoreBoard liveScoreBoard = new LiveScoreBoard();

    @Test
    void startGameShouldAddNewEntryWithInitialScore() {
        // given
        String homeTeam = "home";
        String awayTeam = "away";
        // when
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // then
        assertBoard().containsOnly(new LiveResultDto(homeTeam, 0, awayTeam, 0));
    }

    @Test
    void startGameShouldThrowException_whenSameTeamsAlreadyPlay() {
        // given
        String homeTeam = "home";
        String awayTeam = "away";
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // when
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.startGame(homeTeam, awayTeam));
    }

    @Test
    void startGameShouldThrowException_whenSameTeamProvidedTwice() {
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.startGame("team", "team"));
    }

    @Test
    void startGameShouldThrowException_whenOneTeamCurrentlyPlayAnotherGame() {
        // given
        String homeTeam = "home";
        String awayTeam = "away";
        String anotherAwayTeam = "another away";
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // when
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.startGame(homeTeam, anotherAwayTeam));
    }

    @Test
    void startGameShouldThrowException_whenInvalidTeamProvided() {
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.startGame(null, "away"));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.startGame("home", null));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.startGame("", "away"));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.startGame("home", ""));
    }

    @Test
    void finishGameShouldRemoveGameFromBoard() {
        // given
        String homeTeam = "home";
        String awayTeam = "away";
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // when
        liveScoreBoard.finishGame(homeTeam, awayTeam);
        // then
        assertBoard().isEmpty();
    }

    @Test
    void finishGameShouldThrowException_whenGameDoesNotExist() {
        // given
        String homeTeam = "home";
        String awayTeam = "away";
        // when
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.finishGame(homeTeam, awayTeam));
    }

    @Test
    void shouldUpdateScore() {
        // given
        String homeTeam = "home";
        String awayTeam = "away";
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // when
        liveScoreBoard.updateScore(homeTeam, 3, awayTeam, 1);
        // then
        assertBoard().containsOnly(new LiveResultDto(homeTeam, 3, awayTeam, 1));
    }

    @Test
    void updateScoreShouldThrowException_whenTeamIsIllegal() {
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore(null, 1, "away", 0));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore("home", 1, null, 0));
    }

    @Test
    void updateScoreShouldThrowException_whenScoreIsIllegal() {
        // given
        liveScoreBoard.startGame("home", "away");
        // when / then
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore("home", -1, "away", 0));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore("home", 1000, "away", 0));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore("home", 0, "away", -1));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore("home", 0, "away", 1000));
    }

    @Test
    void summaryByTotalScoreShouldReturnSortedBy_totalScoreDescending_andAddedOrderDescending() {
        // given
        givenCurrentLiveScore("Mexico", 0, "Canada", 5);
        givenCurrentLiveScore("Spain", 10, "Brazil", 2);
        givenCurrentLiveScore("Germany", 2, "France", 2);
        givenCurrentLiveScore("Uruguay", 6, "Italy", 6);
        givenCurrentLiveScore("Argentina", 3, "Australia", 1);
        // when // then
        assertBoard().containsExactly(
                new LiveResultDto("Uruguay", 6, "Italy", 6),
                new LiveResultDto("Spain", 10, "Brazil", 2),
                new LiveResultDto("Mexico", 0, "Canada", 5),
                new LiveResultDto("Argentina", 3, "Australia", 1),
                new LiveResultDto("Germany", 2, "France", 2)
        );
    }

    private ListAssert<LiveResultDto> assertBoard() {
        return assertThat(liveScoreBoard.summaryByTotalScore());
    }

    private void assertIllegalArgumentExceptionIsThrown(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable);
    }

    private void givenCurrentLiveScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        liveScoreBoard.startGame(homeTeam, awayTeam);
        liveScoreBoard.updateScore(homeTeam, homeScore, awayTeam, awayScore);
    }
}