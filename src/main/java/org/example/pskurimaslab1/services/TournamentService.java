package org.example.pskurimaslab1.services;


import org.example.pskurimaslab1.model.dto.TournamentDTO;

import java.util.List;

public interface TournamentService {
    TournamentDTO addTournament(TournamentDTO tournament);
    void deleteTournament(TournamentDTO tournament);
    TournamentDTO updateTournament(TournamentDTO tournament);
    void addTeamToTournament(Long tournamentId, Long teamId);
    void removeTeamFromTournament(Long tournamentId, Long teamId);
    TournamentDTO getTournament(Long id);
    List<TournamentDTO> getTournaments();
    List<TournamentDTO> getTournamentsByTeam(Long teamId);
}
