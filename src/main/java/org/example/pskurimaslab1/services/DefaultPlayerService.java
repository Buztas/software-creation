package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.mappers.PlayerMapper;
import org.example.pskurimaslab1.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public DefaultPlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public Player getPlayer(long id) {
        return playerMapper.getPlayerById(id);
    }

    @Override
    public List<Player> getPlayers() {
        return playerMapper.getAllPlayers();
    }

    @Override
    public void addPlayer(Player player) {
        playerMapper.insertPlayer(player);
    }

    @Override
    public void removePlayer(long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public void removeAllPlayers() {
        playerRepository.deleteAll();
    }

    @Override
    public void updatePlayer(Player player) {
        playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayersByTeam(long teamId) {
        return playerMapper.getPlayersByTeamId(teamId);
    }
}
