package org.sportdatacompany.livescoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LiveScoreBoard {

    private final List<Game> games = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        validateTeams(homeTeam, awayTeam);
        validateTeamDoesNotPlayAnotherGame(homeTeam, awayTeam);
        Game game = new Game(homeTeam, awayTeam);
        this.games.add(game);
    }

    public List<LiveResultDto> getLiveResults() {
        ArrayList<Game> gamesCopy = new ArrayList<>(games);
        Collections.reverse(gamesCopy);
        return gamesCopy.stream()
                .map(LiveScoreBoard::toLiveResultDto)
                .sorted(Comparator.comparingInt(LiveResultDto::scoreSum).reversed())
                .toList();
    }

    private void validateTeams(String homeTeam, String awayTeam) {
        validateTeam(homeTeam);
        validateTeam(awayTeam);
    }

    private void validateTeam(String team) {
        if (team == null || team.isBlank())
            throw new IllegalArgumentException("Team name cannot be null or empty");
    }

    private void validateTeamDoesNotPlayAnotherGame(String homeTeam, String awayTeam) {
        Optional<Game> gameWithSameTeam = this.games.stream()
                .filter(game -> game.hasSameParticipant(homeTeam, awayTeam))
                .findAny();
        if (gameWithSameTeam.isPresent()) {
            throw new IllegalArgumentException("Game with same team already exists: " + gameWithSameTeam.get());
        }
    }

    private static LiveResultDto toLiveResultDto(Game game) {
        return new LiveResultDto(game.getHomeTeam(), game.getHomeScore(), game.getAwayTeam(), game.getAwayScore());
    }
}
