package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.mapper.TournamentMapper;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.model.dto.TournamentDTO;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Profile("test")
public class DefaultTournamentService implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final TournamentMapper tournamentMapper;

    public DefaultTournamentService(TournamentRepository tournamentRepository, TeamRepository teamRepository, TournamentMapper tournamentMapper) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
        this.tournamentMapper = tournamentMapper;
    }

    @Override
    @Transactional
    public TournamentDTO addTournament(TournamentDTO dto) {
        Tournament tournament = tournamentMapper.toEntity(dto);
        return tournamentMapper.toDto(tournamentRepository.save(tournament));
    }

    @Override
    @Transactional
    public void deleteTournament(TournamentDTO dto) {
        Tournament tournament = tournamentMapper.toEntity(dto);

        for (Team team : tournament.getTeams()) {
            team.getTournaments().remove(tournament);
        }

        tournament.getTeams().clear();

        tournamentRepository.save(tournament);
        tournamentRepository.delete(tournament);
    }

    @Override
    @Transactional
    public TournamentDTO updateTournament(TournamentDTO dto) {
        Tournament tournament = tournamentMapper.toEntity(dto);
        return tournamentMapper.toDto(tournamentRepository.save(tournament));
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
    public TournamentDTO getTournament(Long id) {
        return tournamentMapper.toDto(tournamentRepository.findById(id).orElse(null));
    }

    @Override
    public List<TournamentDTO> getTournaments() {
        return tournamentRepository.findAll().stream().map(tournamentMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<TournamentDTO> getTournamentsByTeam(Long teamId) {
        return tournamentRepository.findTournamentsByTeamId(teamId).stream()
                .map(tournamentMapper::toDto)
                .collect(Collectors.toList());
    }
}
