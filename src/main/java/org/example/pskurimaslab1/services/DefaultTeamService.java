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
public class DefaultTeamService implements TeamService {

    private final TeamRepository teamRepository;
    private final TournamentRepository tournamentRepository;

    public DefaultTeamService(TeamRepository teamRepository, TournamentRepository tournamentRepository) {
        this.teamRepository = teamRepository;
        this.tournamentRepository = tournamentRepository;
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
        // Unlink from tournaments manually
        if (team.getTournaments() != null) {
            for (Tournament tournament : team.getTournaments()) {
                // Remove from tournament's team list
                tournament.getTeams().removeIf(t -> t.getId().equals(team.getId()));

                // Reset winner if this team was the winner
                if (team.getName().equals(tournament.getWinner())) {
                    tournament.setWinner(null);
                }
            }
            tournamentRepository.saveAll(team.getTournaments());
        }

        // Now remove relationships and players
        teamRepository.removePlayersByTeamId(team.getId());
        teamRepository.removeTeamTournamentRelationship(team.getId());
        teamRepository.flush();

        teamRepository.deleteById(team.getId());
        teamRepository.flush();
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
