package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.dto.PlayerDTO;
import org.example.pskurimaslab1.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/players")
@CrossOrigin(origins = "*")
public class PlayerRestController {

    private final PlayerService playerService;

    public PlayerRestController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getAllPlayers() {
        return ResponseEntity.ok(playerService.getPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long id) {
        PlayerDTO player = playerService.getPlayer(id);
        return player != null ? ResponseEntity.ok(player) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<PlayerDTO> addPlayer(@RequestBody PlayerDTO playerDTO) {
        PlayerDTO created = playerService.addPlayer(playerDTO);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long id, @RequestBody PlayerDTO incomingDTO) {
        PlayerDTO existing = playerService.getPlayer(id);
        if (existing == null) return ResponseEntity.notFound().build();

        PlayerDTO updatedDTO = new PlayerDTO(
                id,
                incomingDTO.name(),
                incomingDTO.surname(),
                incomingDTO.age(),
                incomingDTO.teamId(),
                incomingDTO.version()
        );

        PlayerDTO updated = playerService.updatePlayer(updatedDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.removePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/async-update")
    public CompletableFuture<PlayerDTO> asyncUpdate(@PathVariable Long id, @RequestBody PlayerDTO playerDTO) throws InterruptedException {
        PlayerDTO updated = new PlayerDTO(
                id,
                playerDTO.name(),
                playerDTO.surname(),
                playerDTO.age(),
                playerDTO.teamId(),
                playerDTO.version()
        );
        return playerService.asyncUpdatePlayer(updated);
    }
}
