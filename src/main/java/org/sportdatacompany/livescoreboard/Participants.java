package org.sportdatacompany.livescoreboard;

record Participants(String homeTeam, String awayTeam) {

    Participants {
        validateTeamName(homeTeam);
        validateTeamName(awayTeam);
        validateTeamIsNotTheSame(homeTeam, awayTeam);
    }

    private void validateTeamName(String team) {
        if (team == null || team.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
        }
    }

    private void validateTeamIsNotTheSame(String homeTeam, String awayTeam) {
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Same team cannot be assigned as home and away");
        }
    }

    boolean hasSameParticipant(Participants participants) {
        if (isOneOfParticipants(participants.homeTeam)) return true;
        return isOneOfParticipants(participants.awayTeam);
    }

    boolean isOneOfParticipants(String team) {
        if (homeTeam.equals(team)) return true;
        return awayTeam.equals(team);
    }
}
