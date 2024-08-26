package org.sportdatacompany.livescoreboard;

record Participants(String homeTeam, String awayTeam) {

    Participants {
        validateTeamName(homeTeam);
        validateTeamName(awayTeam);
    }

    private void validateTeamName(String team) {
        if (team == null || team.isBlank()) {
            throw new IllegalArgumentException("Team name cannot be null or empty");
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
