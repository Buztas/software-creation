package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//TODO: adjust the crud methods

@Service
public class DefaultTournamentService implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    public DefaultTournamentService(TournamentRepository tournamentRepository, TeamRepository teamRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional
    public Tournament addTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
        return tournament;
    }

    @Override
    @Transactional
    public void removeFromTournament(Tournament tournament) {
        // Remove all relationships (teams) before deleting the tournament
        tournamentRepository.removeTournamentTeamRelation(tournament.getId());
        tournamentRepository.deleteById(tournament.getId());
    }

    @Override
    @Transactional
    public Tournament updateTournament(Tournament tournament) {
        tournamentRepository.save(tournament);
        return tournament;
    }

    @Override
    @Transactional
    public void addTeamToTournament(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);

        if (tournament != null && team != null) {
            tournament.getTeams().add(team);
            team.getTournaments().add(tournament);
            tournamentRepository.save(tournament);
            teamRepository.save(team);
        }
    }

    @Override
    @Transactional
    public void removeTeamFromTournament(Long teamId, Long tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);

        if (tournament != null && team != null) {
            tournament.getTeams().removeIf(t -> t.getId().equals(teamId));
            team.getTournaments().removeIf(t -> t.getId().equals(tournamentId));

            tournamentRepository.save(tournament);
            teamRepository.save(team);
        }
    }

    @Override
    @Transactional
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Tournament> getTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    public List<Tournament> getTournamentsByTeam(Long teamId) {
        return tournamentRepository.findTournamentsByTeamId(teamId);
    }
}
