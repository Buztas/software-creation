package org.example.pskurimaslab1.services;

import jakarta.persistence.OptimisticLockException;
import org.example.pskurimaslab1.mapper.TeamMapper;
import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.model.dto.TeamDTO;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Primary
// CDI Specialized
public class LoggingTeamService implements TeamService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamMapper teamMapper;
    private final Logger logger = Logger.getLogger(LoggingTeamService.class.getName());

    public LoggingTeamService(TeamRepository teamRepository,
                              TournamentRepository tournamentRepository,
                              TeamMapper teamMapper) {
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
        this.teamMapper = teamMapper;
    }

    @Override
    @Transactional
    public TeamDTO addTeam(TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);
        Team saved = teamRepository.save(team);
        logger.info("Team created: ID=" + saved.getId() + ", Name='" + saved.getName() + "'");
        return teamMapper.toDto(saved);
    }

    @Override
    @Transactional
    public void addTeamToTournament(Long teamId, Long tournamentId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("Team not found: ID=" + teamId));
        teamRepository.addTeamToTournament(teamId, tournamentId);
        logger.info("Team added to tournament: TeamID=" + teamId + " ('" + team.getName() + "'), TournamentID=" + tournamentId);
    }

    @Override
    @Transactional
    public void deleteTeam(TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);
        logger.info("Attempting to delete team: ID=" + team.getId() + ", Name='" + team.getName() + "'");

        for (Tournament tournament : team.getTournaments()) {
            tournament.getTeams().remove(team);
            if (team.getName().equals(tournament.getWinner())) {
                logger.info("Resetting winner for tournament ID=" + tournament.getId());
                tournament.setWinner(null);
            }
        }

        team.getTournaments().clear();

        for (Player p : team.getPlayers()) {
            p.setTeam(null);
        }

        team.getPlayers().clear();

        teamRepository.save(team);
        teamRepository.delete(team);

        logger.info("Team deleted successfully: ID=" + team.getId());
    }

    @Override
    @Transactional
    public TeamDTO updateTeam(TeamDTO dto) {
        Team team = teamMapper.toEntity(dto);
        logger.info("Updating team: ID=" + team.getId() + ", Name='" + team.getName() + "'");
        try {
            Team updated = teamRepository.save(team);
            logger.info("Team updated successfully: ID=" + updated.getId() + ", Version=" + updated.getVersion());
            return teamMapper.toDto(updated);
        } catch (OptimisticLockException ex) {
            logger.log(Level.WARNING, "Optimistic lock conflict while updating team: ID=" + team.getId(), ex);
            throw ex;
        }
    }

    @Override
    @Transactional
    public void removePlayerFromTeam(Long teamId, Long playerId) {
        teamRepository.removePlayerFromTeam(teamId, playerId);
        logger.info("Removed player from team: PlayerID=" + playerId + ", TeamID=" + teamId);
    }

    @Override
    public List<TeamDTO> getTeams() {
        logger.info("Fetching all teams");
        return teamRepository.findAll().stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TeamDTO getTeam(Long id) {
        logger.info("Fetching team by ID: " + id);
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            logger.info("Team found: ID=" + id + ", Name='" + team.get().getName() + "'");
        } else {
            logger.warning("Team not found: ID=" + id);
        }
        return team.map(teamMapper::toDto).orElse(null);
    }

    @Override
    public List<TeamDTO> getTeamsByTournamentId(Long tournamentId) {
        logger.info("Fetching teams for tournament: ID=" + tournamentId);
        return teamRepository.findTeamsByTournamentId(tournamentId).stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
    }
}
