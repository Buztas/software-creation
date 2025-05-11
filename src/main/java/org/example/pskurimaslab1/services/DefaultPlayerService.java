package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//TODO: adjust the crud methods

@Service
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;

    public DefaultPlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
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
    public Player addPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    @Transactional
    public void removePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void removeAllPlayers() {
        playerRepository.deleteAll();
    }

    @Override
    @Transactional
    public Player updatePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public List<Player> getPlayersByTeam(Long teamId) {
        return playerRepository.findPlayersByTeamId(teamId);
    }
}
