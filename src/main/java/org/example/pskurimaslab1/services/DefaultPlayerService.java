package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.mappers.PlayerMapper;
import org.example.pskurimaslab1.repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Player getPlayer(Long id) {
        return playerRepository.findPlayerById(id);
    }

    @Override
    public List<Player> getPlayers() {
        return playerRepository.findAll();
    }

    @Override
    @Transactional
    public void addPlayer(Player player) {
        playerMapper.insertPlayer(player);
    }

    @Override
    @Transactional
    public void removePlayer(Long id) {
        playerMapper.deletePlayer(id);
    }

    @Override
    @Transactional
    public void removeAllPlayers() {
        playerRepository.deleteAll();
    }

    @Override
    @Transactional
    public void updatePlayer(Player player) {
        playerMapper.updatePlayer(player);
    }

    @Override
    public List<Player> getPlayersByTeam(Long teamId) {
        return playerRepository.findPlayersByTeamId(teamId);
    }
}