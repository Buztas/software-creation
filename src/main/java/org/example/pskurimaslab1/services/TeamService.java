package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

public interface TeamService {
    void addTeam(Team team);
    void addTeamToTournament(long teamId, long tournamentId);
    void deleteTeam(Team team);
    void updateTeam(Team team);
    List<Team> getTeams();
    Team getTeam(int id);
    List<Tournament> getTournamentsByTeam(long teamId);
}
