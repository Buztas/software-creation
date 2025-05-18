package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.mapper.TeamMapper;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.model.dto.TeamDTO;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultTeamService implements TeamService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamMapper teamMapper;

    public DefaultTeamService(TeamRepository teamRepository, TournamentRepository tournamentRepository, TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    @Transactional
    public TeamDTO addTeam(TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);
        return teamMapper.toDto(teamRepository.save(team));
    }

    @Override
    @Transactional
    public void addTeamToTournament(Long teamId, Long tournamentId) {
        teamRepository.addTeamToTournament(teamId, tournamentId);
    }

    @Override
    @Transactional
    public void deleteTeam(TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);

        for (Tournament tournament : team.getTournaments()) {
            tournament.getTeams().remove(team);
        }

        team.getTournaments().clear();
        team.getPlayers().forEach(p -> p.setTeam(null));
        team.getPlayers().clear();

        teamRepository.save(team);
        teamRepository.delete(team);
    }

    @Override
    @Transactional
    public TeamDTO updateTeam(TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);
        return teamMapper.toDto(teamRepository.save(team));
    }

    @Override
    @Transactional
    public void removePlayerFromTeam(Long teamId, Long playerId) {
        teamRepository.removePlayerFromTeam(teamId, playerId);
    }

    @Override
    public List<TeamDTO> getTeams() {
        return teamRepository.findAll().stream().map(teamMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public TeamDTO getTeam(Long id) {
        return teamMapper.toDto(teamRepository.findById(id).orElse(null));
    }

    @Override
    public List<TeamDTO> getTeamsByTournamentId(Long tournamentId) {
        return teamRepository.findTeamsByTournamentId(tournamentId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }
}
