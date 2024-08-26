package org.sportdatacompany;

import org.sportdatacompany.livescoreboard.LiveScoreBoard;

public class Main {
    public static void main(String[] args) {
        LiveScoreBoard liveScoreBoard = new LiveScoreBoard();
        liveScoreBoard.startGame("Poland", "Germany");
        liveScoreBoard.updateScore("Poland", 1, "Germany", 0);
        liveScoreBoard.summaryByTotalScore().forEach(System.out::println);
    }
}