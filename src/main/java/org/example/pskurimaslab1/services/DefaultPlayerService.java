package org.example.pskurimaslab1.services;

import jakarta.persistence.OptimisticLockException;
import org.example.pskurimaslab1.mapper.PlayerMapper;
import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.dto.PlayerDTO;
import org.example.pskurimaslab1.repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public DefaultPlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        this.playerRepository = playerRepository;
        this.playerMapper = playerMapper;
    }

    @Override
    public PlayerDTO getPlayer(Long id) {
        return playerMapper.toDto(playerRepository.findPlayerById(id));
    }

    @Override
    public List<PlayerDTO> getPlayers() {
        return playerRepository.findAll().stream().map(playerMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PlayerDTO addPlayer(PlayerDTO dto) {
        Player player = playerMapper.toEntity(dto);
        return playerMapper.toDto(playerRepository.save(player));
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

    @Transactional
    @Override
    public PlayerDTO updatePlayer(PlayerDTO dto) {
        Player existing = playerRepository.findById(dto.id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        existing.setName(dto.name());
        existing.setSurname(dto.surname());
        existing.setAge(dto.age());
        existing.setTeam(playerMapper.toEntity(dto).getTeam());

        return playerMapper.toDto(existing);
    }

    @Override
    public List<PlayerDTO> getPlayersByTeam(Long teamId) {
        return playerRepository.findPlayersByTeamId(teamId).stream()
                .map(playerMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompletableFuture<PlayerDTO> asyncUpdatePlayer(PlayerDTO dto) throws InterruptedException {
        Thread.sleep(5000);
        return CompletableFuture.completedFuture(updatePlayer(dto));
    }
}
