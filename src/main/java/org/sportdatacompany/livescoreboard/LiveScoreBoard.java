package org.sportdatacompany.livescoreboard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class LiveScoreBoard {

    private final List<LiveGameScore> liveScores = new ArrayList<>();

    public void startGame(String homeTeam, String awayTeam) {
        Participants participants = new Participants(homeTeam, awayTeam);
        validateTeamDoesNotPlayOtherGame(participants);
        LiveGameScore liveGameScore = new LiveGameScore(participants);
        this.liveScores.add(liveGameScore);
    }

    public void finishGame(String homeTeam, String awayTeam) {
        Participants participants = new Participants(homeTeam, awayTeam);
        LiveGameScore liveGameScore = findLiveGameScore(participants);
        this.liveScores.remove(liveGameScore);
    }

    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        Participants participants = new Participants(homeTeam, awayTeam);
        Score newScore = new Score(homeScore, awayScore);
        LiveGameScore liveGameScore = findLiveGameScore(participants);
        liveGameScore.updateScore(newScore);
    }

    public List<LiveResultDto> summaryByTotalScore() {
        return liveScoresViewSortedByAddedOrderDescending().stream()
                .sorted(byTotalScoreDescending())
                .map(LiveScoreBoard::toLiveResultDto)
                .toList();
    }

    private LiveGameScore findLiveGameScore(Participants participants) {
        return this.liveScores.stream()
                .filter(liveGameScore -> liveGameScore.getParticipants().equals(participants))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Game '%s' does no exist", participants)));
    }

    private void validateTeamDoesNotPlayOtherGame(Participants participants) {
        Optional<LiveGameScore> foundGame = this.liveScores.stream()
                .filter(liveGameScore -> liveGameScore.hasSameParticipant(participants))
                .findAny();
        if (foundGame.isPresent()) {
            throw new IllegalArgumentException("One of participants plays another game: " + foundGame.get().getParticipants());
        }
    }

    private static Comparator<LiveGameScore> byTotalScoreDescending() {
        return Comparator.comparingInt(LiveGameScore::getTotalScore).reversed();
    }

    private ArrayList<LiveGameScore> liveScoresViewSortedByAddedOrderDescending() {
        ArrayList<LiveGameScore> gamesCopy = new ArrayList<>(liveScores);
        Collections.reverse(gamesCopy);
        return gamesCopy;
    }

    private static LiveResultDto toLiveResultDto(LiveGameScore liveGameScore) {
        Participants participants = liveGameScore.getParticipants();
        Score score = liveGameScore.getScore();
        return new LiveResultDto(participants.homeTeam(), score.getHomeScore(), participants.awayTeam(), score.getAwayScore());
    }
}
