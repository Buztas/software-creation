package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Player;

import java.util.List;

public interface PlayerService {
    Player getPlayer(long id);
    List<Player> getPlayers();
    void addPlayer(Player player);
    void removePlayer(long id);
    void removeAllPlayers();
    void updatePlayer(Player player);
    List<Player> getPlayersByTeam(long teamId);
}
