package org.example.pskurimaslab1.services;


import org.example.pskurimaslab1.model.dto.PlayerDTO;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface PlayerService {
    PlayerDTO getPlayer(Long id);
    List<PlayerDTO> getPlayers();
    PlayerDTO addPlayer(PlayerDTO player);
    void removePlayer(Long id);
    void removeAllPlayers();
    PlayerDTO updatePlayer(PlayerDTO player);
    List<PlayerDTO> getPlayersByTeam(Long teamId);
    CompletableFuture<PlayerDTO> asyncUpdatePlayer(PlayerDTO player) throws InterruptedException;
}
