package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//TODO: adjust the crud methods

@Service
public class DefaultTeamService implements TeamService {

    private final TeamRepository teamRepository;

    public DefaultTeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional
    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    @Transactional
    public void addTeamToTournament(Long teamId, Long tournamentId) {
        teamRepository.addTeamToTournament(teamId, tournamentId);
    }

    @Override
    @Transactional
    public void deleteTeam(Team team) {
        teamRepository.removePlayersByTeamId(team.getId());
        teamRepository.removeTeamTournamentRelationship(team.getId());
        teamRepository.deleteById(team.getId());
    }

    @Override
    @Transactional
    public Team updateTeam(Team team) {
        return teamRepository.save(team);
    }

    @Override
    @Transactional
    public void removePlayerFromTeam(Long teamId, Long playerId) {
        teamRepository.removePlayerFromTeam(teamId, playerId);
    }

    @Override
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team getTeam(Long id) {
        return teamRepository.findById(id).orElse(null);
    }

    @Override
    public List<Team> getTeamsByTournamentId(Long tournamentId) {
        return teamRepository.findTeamsByTournamentId(tournamentId);
    }
}
