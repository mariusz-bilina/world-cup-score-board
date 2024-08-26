package org.sportdatacompany.livescoreboard;

class LiveGameScore {

    private final Participants participants;
    private Score score;

    LiveGameScore(Participants participants) {
        this.participants = participants;
        this.score = new Score(0, 0);
    }

    boolean hasSameParticipant(Participants participants) {
        return this.participants.hasSameParticipant(participants);
    }

    void updateScore(Score score) {
        this.score = score;
    }

    int getTotalScore() {
        return score.getTotalScore();
    }

    Participants getParticipants() {
        return participants;
    }

    Score getScore() {
        return score;
    }
}
