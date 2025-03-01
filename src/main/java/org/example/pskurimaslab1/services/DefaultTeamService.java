package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.mappers.TeamMapper;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void addTeam(Team team) {
        teamMapper.insertTeam(team);
    }

    @Override
    public void addTeamToTournament(long teamId, long tournamentId) {
        teamMapper.addTeamToTournament(teamId, tournamentId);
    }

    @Override
    public void deleteTeam(Team team) {
        teamRepository.delete(team);
    }

    @Override
    public void updateTeam(Team team) {
        teamRepository.save(team);
    }

    @Override
    public List<Team> getTeams() {
        return teamMapper.getAllTeams();
    }

    @Override
    public Team getTeam(int id) {
        return teamMapper.getTeamById(id);
    }

    @Override
    public List<Tournament> getTournamentsByTeam(long teamId) {
        return teamMapper.getTournamentsByTeamId(teamId);
    }
}
