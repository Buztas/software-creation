package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.Team;

import java.util.List;

public interface PlayerService {
    Player getPlayer(Long id);
    List<Player> getPlayers();
    Player addPlayer(Player player);
    void removePlayer(Long id);
    void removeAllPlayers();
    Player updatePlayer(Player player);
    List<Player> getPlayersByTeam(Long teamId);
}
