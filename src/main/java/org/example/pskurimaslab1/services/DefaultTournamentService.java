package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.mappers.TournamentMapper;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultTournamentService implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TournamentMapper tournamentMapper;

    public DefaultTournamentService(TournamentRepository tournamentRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.tournamentMapper = tournamentMapper;
    }

    @Override
    public void addTournament(Tournament tournament) {
        tournamentMapper.insertTournament(tournament);
    }

    @Override
    public void removeFromTournament(Tournament tournament) {
        tournamentRepository.delete(tournament);
    }

    @Override
    public void updateTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
    }

    @Override
    public Tournament getTournament(long id) {
        return tournamentMapper.getTournament(id);
    }

    @Override
    public List<Tournament> getTournaments() {
        return tournamentMapper.getTournaments();
    }

    @Override
    public List<Team> getTeamsByTournamentId(long tournamentId) {
        return tournamentMapper.getAllTeamsByTournamentId(tournamentId);
    }
}
