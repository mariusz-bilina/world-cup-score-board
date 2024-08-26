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
        String homeTeam = "home team";
        String awayTeam = "away team";
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // when
        liveScoreBoard.finishGame(homeTeam, awayTeam);
        // then
        assertBoard().isEmpty();
    }

    @Test
    void finishGameShouldThrowException_whenGameDoesNotExist() {
        // given
        String homeTeam = "home team";
        String awayTeam = "away team";
        // when
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.finishGame(homeTeam, awayTeam));
    }

    @Test
    void shouldUpdateScore() {
        // given
        String homeTeam = "home team";
        String awayTeam = "away team";
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // when
        liveScoreBoard.updateScore(homeTeam, 3, awayTeam, 1);
        // then
        assertBoard().containsOnly(new LiveResultDto(homeTeam, 3, awayTeam, 1));
    }

    @Test
    void updateScoreShouldThrowException_whenGameDoesNotExist() {
        // given
        String homeTeam = "home team";
        String awayTeam = "away team";
        liveScoreBoard.startGame(homeTeam, awayTeam);
        // when
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore(homeTeam, 1, "nonexisting team", 0));
    }

    @Test
    void updateScoreShouldThrowException_whenTeamIsNull() {
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore(null, 1, "away team", 0));
        assertIllegalArgumentExceptionIsThrown(() -> liveScoreBoard.updateScore("home team", 1, null, 0));
    }

    private ListAssert<LiveResultDto> assertBoard() {
        return assertThat(liveScoreBoard.getLiveResults());
    }

    private void assertIllegalArgumentExceptionIsThrown(ThrowableAssert.ThrowingCallable throwingCallable) {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(throwingCallable);
    }
}