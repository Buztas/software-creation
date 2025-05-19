package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.dto.TeamDTO;
import org.example.pskurimaslab1.model.dto.TournamentDTO;
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
    public ResponseEntity<List<TeamDTO>> getAllTeams() {
        return ResponseEntity.ok(teamService.getTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDTO> getTeamById(@PathVariable Long id) {
        TeamDTO team = teamService.getTeam(id);
        return team != null ? ResponseEntity.ok(team) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<TeamDTO> addTeam(@RequestBody TeamDTO teamDTO) {
        TeamDTO created = teamService.addTeam(teamDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDTO> updateTeam(@PathVariable Long id, @RequestBody TeamDTO incomingDTO) {
        TeamDTO existing = teamService.getTeam(id);
        if (existing == null) return ResponseEntity.notFound().build();

        TeamDTO updatedDTO = new TeamDTO(
                existing.id(),
                incomingDTO.name(),
                incomingDTO.sport(),
                incomingDTO.playerIds(),
                incomingDTO.tournamentIds(),
                incomingDTO.version()
        );

        TeamDTO updated = teamService.updateTeam(updatedDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        TeamDTO team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(team);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{teamId}/tournaments")
    public ResponseEntity<List<TournamentDTO>> getTournamentsByTeam(@PathVariable Long teamId) {
        return ResponseEntity.ok(tournamentService.getTournamentsByTeam(teamId));
    }

    @PostMapping("/{teamId}/add-to-tournament/{tournamentId}")
    public ResponseEntity<Void> addTeamToTournament(@PathVariable Long teamId, @PathVariable Long tournamentId) {
        teamService.addTeamToTournament(teamId, tournamentId);
        return ResponseEntity.ok().build();
    }
}
