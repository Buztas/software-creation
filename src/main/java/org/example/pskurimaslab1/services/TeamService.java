package org.example.pskurimaslab1.services;


import org.example.pskurimaslab1.model.dto.TeamDTO;

import java.util.List;

public interface TeamService {
    TeamDTO addTeam(TeamDTO team);
    void addTeamToTournament(Long teamId, Long tournamentId);
    void deleteTeam(TeamDTO team);
    TeamDTO updateTeam(TeamDTO team);
    void removePlayerFromTeam(Long teamId, Long playerId);
    List<TeamDTO> getTeams();
    TeamDTO getTeam(Long id);
    List<TeamDTO> getTeamsByTournamentId(Long tournamentId);
}
