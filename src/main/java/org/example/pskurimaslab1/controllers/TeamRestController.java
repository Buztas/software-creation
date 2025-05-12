package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.services.TeamService;
import org.example.pskurimaslab1.services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
@CrossOrigin(origins = "*")
public class TeamRestController {

    private final TeamService teamService;
    private final TournamentService tournamentService;

    public TeamRestController(TeamService teamService, TournamentService tournamentService) {
        this.teamService = teamService;
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        Team team = teamService.getTeam(id);
        return team != null ? ResponseEntity.ok(team) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Team> addTeam(@RequestBody Team team) {
        Team createdTeam = teamService.addTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team incomingTeam) {
        Team existing = teamService.getTeam(id);
        if (existing == null) return ResponseEntity.notFound().build();

        existing.setName(incomingTeam.getName());
        existing.setSport(incomingTeam.getSport());

        Team updated = teamService.updateTeam(existing);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        Team team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(team);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{teamId}/tournaments")
    public ResponseEntity<List<Tournament>> getTournamentsByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(tournamentService.getTournamentsByTeam(teamId));
    }

    @PostMapping("/{teamId}/add-to-tournament/{tournamentId}")
    public ResponseEntity<Void> addTeamToTournament(@PathVariable Long teamId, @PathVariable Long tournamentId) {
        teamService.addTeamToTournament(teamId, tournamentId);
        return ResponseEntity.ok().build();
    }
}
