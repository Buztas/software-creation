package org.example.pskurimaslab1.mapper;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.dto.PlayerDTO;
import org.example.pskurimaslab1.resolvers.TeamIdResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { TeamIdResolver.class })
public interface PlayerMapper {
    @Mapping(source = "team.id", target = "teamId")
    PlayerDTO toDto(Player player);

    @Mapping(source = "teamId", target = "team", qualifiedByName = "fromId")
    @Mapping(source = "version", target = "version")
    Player toEntity(PlayerDTO dto);
}
