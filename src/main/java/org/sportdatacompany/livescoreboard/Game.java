package org.sportdatacompany.livescoreboard;

class Game {

    private final String homeTeam;
    private final String awayTeam;
    private int homeScore;
    private int awayScore;

    Game(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
    }

    boolean hasSameParticipant(String homeTeam, String awayTeam) {
        return isEqual(this.homeTeam, homeTeam)
                || isEqual(this.homeTeam, awayTeam)
                || isEqual(this.awayTeam, homeTeam)
                || isEqual(this.awayTeam, awayTeam);
    }

    boolean isEqual(String first, String second) {
        return first.equals(second);
    }

    public void updateResult(int homeScore, int awayScore) {
        this.homeScore = homeScore;
        this.awayScore = awayScore;
    }

    String getHomeTeam() {
        return homeTeam;
    }

    String getAwayTeam() {
        return awayTeam;
    }

    int getHomeScore() {
        return homeScore;
    }

    int getAwayScore() {
        return awayScore;
    }
}
