package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.mappers.TeamMapper;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultTeamService implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMapper teamMapper;

    public DefaultTeamService(TeamRepository teamRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    @Transactional
    public void addTeam(Team team) {
        teamMapper.insertTeam(team);
    }

    @Override
    @Transactional
    public void addTeamToTournament(Long teamId, Long tournamentId) {
        teamMapper.addTeamToTournament(teamId, tournamentId);
    }

    @Override
    @Transactional
    public void deleteTeam(Team team) {
        teamMapper.removePlayersByTeamId(team.getId());
        teamMapper.removeTeamTournamentRelationship(team.getId());
        teamMapper.deleteTeam(team.getId());
    }

    @Override
    @Transactional
    public void updateTeam(Team team) {
        teamMapper.updateTeam(team);
    }

    @Override
    @Transactional
    public void removePlayerFromTeam(Long teamId, Long playerId) {
        teamMapper.removePlayerFromTeam(teamId, playerId);
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