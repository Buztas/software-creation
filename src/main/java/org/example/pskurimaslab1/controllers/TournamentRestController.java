package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
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
    public List<Tournament> getAllTournaments() {
        return tournamentService.getTournaments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        Tournament tournament = tournamentService.getTournament(id);
        return tournament != null ? ResponseEntity.ok(tournament) : ResponseEntity.notFound().build();
    }

    @PostMapping(produces = "application/json")
    public @ResponseBody Tournament addTournament(@RequestBody Tournament tournament) {
        return tournamentService.addTournament(tournament);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Tournament> updateTournament(@PathVariable Long id, @RequestBody Tournament tournament) {
        tournament.setId(id);
        Tournament updated = tournamentService.updateTournament(tournament);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            tournamentService.removeFromTournament(tournament);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{tournamentId}/teams")
    public List<Team> getTeamsByTournament(@PathVariable Long tournamentId) {
        return teamService.getTeamsByTournamentId(tournamentId);
    }

    @PostMapping("/{tournamentId}/add-team")
    public ResponseEntity<Void> addTeamToTournament(@PathVariable Long tournamentId, @RequestParam Long teamId) {
        tournamentService.addTeamToTournament(tournamentId, teamId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tournamentId}/remove-team/{teamId}")
    public ResponseEntity<Void> removeTeamFromTournament(@PathVariable Long tournamentId, @PathVariable Long teamId) {
        tournamentService.removeTeamFromTournament(teamId, tournamentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{tournamentId}/available-teams")
    public ResponseEntity<List<Team>> getAvailableTeams(@PathVariable Long tournamentId) {
        Tournament tournament = tournamentService.getTournament(tournamentId);
        if (tournament == null) {
            return ResponseEntity.notFound().build();
        }

        List<Team> allTeams = teamService.getTeams().stream()
                .filter(team -> team.getTournaments().stream()
                        .noneMatch(existingTournament -> isConflicting(tournament, existingTournament)))
                .collect(Collectors.toList());

        return ResponseEntity.ok(allTeams);
    }

    private boolean isConflicting(Tournament newTournament, Tournament existingTournament) {
        return !(newTournament.getEndDate().before(existingTournament.getStartDate()) ||
                newTournament.getStartDate().after(existingTournament.getEndDate()));
    }
}
