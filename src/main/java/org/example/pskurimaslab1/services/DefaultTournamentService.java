package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.mappers.TeamMapper;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.mappers.TournamentMapper;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultTournamentService implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final TournamentMapper tournamentMapper;
    private final TeamMapper teamMapper;

    public DefaultTournamentService(TournamentRepository tournamentRepository, TeamRepository teamRepository, TournamentMapper tournamentMapper, TeamMapper teamMapper) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.tournamentMapper = tournamentMapper;
        this.teamMapper = teamMapper;
    }

    @Override
    @Transactional
    public void addTournament(Tournament tournament) {
        tournamentMapper.insertTournament(tournament);
    }

    @Override
    @Transactional
    public void removeFromTournament(Tournament tournament) {
        tournamentMapper.removeTournamentTeamRelation(tournament.getId());
        tournamentMapper.deleteTournament(tournament.getId());
    }

    @Override
    @Transactional
    public void updateTournament(Tournament tournament) {
        tournamentMapper.updateTournament(tournament);
    }

    @Override
    @Transactional
    public void addTeamToTournament(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId.intValue()).orElse(null);
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
    public void removeTeamFromTournament(Long tournamentId, Long teamId) {
        Tournament tournament = tournamentRepository.findById(tournamentId.intValue()).orElse(null);
        Team team = teamRepository.findById(teamId).orElse(null);

        if (tournament != null && team != null) {
            tournament.getTeams().remove(team);
            team.getTournaments().remove(tournament);
            teamMapper.removeTeamFromTournament(tournamentId, teamId);
            tournamentRepository.save(tournament);
        }
    }

    @Override
    @Transactional
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id.intValue()).orElse(null);
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