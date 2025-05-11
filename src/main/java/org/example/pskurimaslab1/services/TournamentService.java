package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

public interface TournamentService {
    Tournament addTournament(Tournament tournament);
    void removeFromTournament(Tournament tournament);
    Tournament updateTournament(Tournament tournament);
    void addTeamToTournament(Long tournamentId, Long teamId);
    void removeTeamFromTournament(Long tournamentId, Long teamId);
    Tournament getTournament(Long id);
    List<Tournament> getTournaments();
    List<Tournament> getTournamentsByTeam(Long teamId);
}
