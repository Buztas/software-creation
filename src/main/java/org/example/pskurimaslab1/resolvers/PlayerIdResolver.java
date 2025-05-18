package org.example.pskurimaslab1.resolvers;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.repositories.PlayerRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerIdResolver {
    private final PlayerRepository playerRepository;

    public PlayerIdResolver(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Named("fromId")
    public Player fromId(Long id) {
        return id == null ? null : playerRepository.findById(id).orElse(null);
    }

    @Named("fromIds")
    public List<Player> fromIds(List<Long> ids) {
        return ids == null ? List.of() : ids.stream()
                .map(this::fromId)
                .collect(Collectors.toList());
    }
}
