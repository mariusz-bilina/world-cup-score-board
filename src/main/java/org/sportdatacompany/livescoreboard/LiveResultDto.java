package org.sportdatacompany.livescoreboard;

public record LiveResultDto(String home, int homeScore, String away, int awayScore) {

    int scoreSum() {
        return homeScore + awayScore;
    }
}
