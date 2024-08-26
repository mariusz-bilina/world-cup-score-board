package org.sportdatacompany.livescoreboard;

class Score {

    private final int homeScore;
    private final int awayScore;

    Score(int homeScore, int awayScore) {
        validateScore(homeScore);
        validateScore(awayScore);
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    private void validateScore(int score) {
        if (score < 0 || score > 999) throw new IllegalArgumentException("Score needs to be between 0 and 999, given: " + score);
    }

    int getHomeScore() {
        return homeScore;
    }

    int getAwayScore() {
        return awayScore;
    }

    public int getTotalScore() {
        return homeScore + awayScore;
    }
}
