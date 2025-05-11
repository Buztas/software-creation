package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

public interface TeamService {
    Team addTeam(Team team);
    void addTeamToTournament(Long teamId, Long tournamentId);
    void deleteTeam(Team team);
    Team updateTeam(Team team);
    void removePlayerFromTeam(Long teamId, Long playerId);
    List<Team> getTeams();
    Team getTeam(Long id);
    List<Team> getTeamsByTournamentId(Long tournamentId);
}
