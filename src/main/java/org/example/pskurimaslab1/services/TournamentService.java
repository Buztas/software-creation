package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

public interface TournamentService {
    void addTournament(Tournament tournament);
    void removeFromTournament(Tournament tournament);
    void updateTournament(Tournament tournament);
    Tournament getTournament(long id);
    List<Tournament> getTournaments();
    List<Team> getTeamsByTournamentId(long tournamentId);
}
