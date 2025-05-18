package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.mapper.PlayerMapper;
import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.dto.PlayerDTO;
import org.example.pskurimaslab1.repositories.PlayerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Primary
@EnableAsync
public class AsyncPlayerService extends DefaultPlayerService {
    public AsyncPlayerService(PlayerRepository playerRepository, PlayerMapper playerMapper) {
        super(playerRepository, playerMapper);
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<PlayerDTO> asyncUpdatePlayer(PlayerDTO player) throws InterruptedException {
        Thread.sleep(5000);
        return CompletableFuture.completedFuture(super.updatePlayer(player));
    }
}
