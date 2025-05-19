package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.dto.TeamDTO;
import org.example.pskurimaslab1.model.dto.TournamentDTO;
import org.example.pskurimaslab1.services.TeamService;
import org.example.pskurimaslab1.services.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tournaments")
@CrossOrigin(origins = "*")
public class TournamentRestController {

    private final TournamentService tournamentService;
    private final TeamService teamService;

    public TournamentRestController(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @GetMapping
    public List<TournamentDTO> getAllTournaments() {
        return tournamentService.getTournaments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TournamentDTO> getTournamentById(@PathVariable Long id) {
        TournamentDTO tournament = tournamentService.getTournament(id);
        return tournament != null ? ResponseEntity.ok(tournament) : ResponseEntity.notFound().build();
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<TournamentDTO> addTournament(@RequestBody TournamentDTO tournamentDTO) {
        TournamentDTO created = tournamentService.addTournament(tournamentDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<TournamentDTO> updateTournament(@PathVariable Long id, @RequestBody TournamentDTO incomingDTO) {
        TournamentDTO existing = tournamentService.getTournament(id);
        if (existing == null) return ResponseEntity.notFound().build();

        TournamentDTO toUpdate = new TournamentDTO(
                id,
                incomingDTO.name(),
                incomingDTO.sport(),
                incomingDTO.startDate(),
                incomingDTO.endDate(),
                incomingDTO.winner(),
                incomingDTO.teamIds(),
                incomingDTO.version()
        );

        TournamentDTO updated = tournamentService.updateTournament(toUpdate);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        TournamentDTO tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            tournamentService.deleteTournament(tournament);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tournamentId}/teams")
    public List<TeamDTO> getTeamsByTournament(@PathVariable Long tournamentId) {
        return teamService.getTeamsByTournamentId(tournamentId);
    }

    @PostMapping("/{tournamentId}/add-team")
    public ResponseEntity<Void> addTeamToTournament(@PathVariable Long tournamentId, @RequestParam Long teamId) {
        tournamentService.addTeamToTournament(tournamentId, teamId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tournamentId}/remove-team/{teamId}")
    public ResponseEntity<Void> removeTeamFromTournament(@PathVariable Long tournamentId, @PathVariable Long teamId) {
        tournamentService.removeTeamFromTournament(tournamentId, teamId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{tournamentId}/available-teams")
    public ResponseEntity<List<TeamDTO>> getAvailableTeams(@PathVariable Long tournamentId) {
        TournamentDTO tournament = tournamentService.getTournament(tournamentId);
        if (tournament == null) {
            return ResponseEntity.notFound().build();
        }

        List<TeamDTO> allTeams = teamService.getTeams().stream()
                .filter(team -> team.tournamentIds().stream()
                        .map(tournamentService::getTournament)
                        .noneMatch(existing -> isConflicting(tournament, existing)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(allTeams);
    }

    private boolean isConflicting(TournamentDTO newTournament, TournamentDTO existingTournament) {
        return !(newTournament.endDate().before(existingTournament.startDate()) ||
                newTournament.startDate().after(existingTournament.endDate()));
    }
}
