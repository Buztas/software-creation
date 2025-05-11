package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.services.PlayerService;
import org.example.pskurimaslab1.services.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerRestController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerRestController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping
    public List<Player> getAllPlayers() {
        return playerService.getPlayers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable Long id) {
        Player player = playerService.getPlayer(id);
        if (player != null) {
            return ResponseEntity.ok(player);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Player> addPlayer(@RequestBody Player player) {
        if (player.getTeam() != null && player.getTeam().getId() != null) {
            Team team = teamService.getTeam(player.getTeam().getId());
            player.setTeam(team);
        }
        Player savedPlayer = playerService.addPlayer(player);
        return ResponseEntity.ok(savedPlayer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Player> updatePlayer(@PathVariable Long id, @RequestBody Player player) {
        player.setId(id);
        if (player.getTeam() != null && player.getTeam().getId() != null) {
            Team team = teamService.getTeam(player.getTeam().getId());
            player.setTeam(team);
        }
        Player updatedPlayer = playerService.updatePlayer(player);
        return ResponseEntity.ok(updatedPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.removePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
