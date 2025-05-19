package org.example.pskurimaslab1.mapper;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.dto.TeamDTO;
import org.example.pskurimaslab1.resolvers.PlayerIdResolver;
import org.example.pskurimaslab1.resolvers.TournamentIdResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { TournamentIdResolver.class, PlayerIdResolver.class })
public interface TeamMapper {
    @Mapping(target = "playerIds", expression = "java(mapPlayerIds(team))")
    @Mapping(target = "tournamentIds", expression = "java(mapTournamentIds(team))")
    @Mapping(source = "version", target = "version")
    TeamDTO toDto(Team team);

    @Mapping(source = "playerIds", target = "players", qualifiedByName = "fromIds")
    @Mapping(source = "tournamentIds", target = "tournaments", qualifiedByName = "fromIds")
    @Mapping(source = "version", target = "version")
    Team toEntity(TeamDTO dto);

    default List<Long> mapPlayerIds(Team team) {
        return team.getPlayers() != null
                ? team.getPlayers().stream().map(p -> p.getId()).collect(Collectors.toList())
                : null;
    }

    default List<Long> mapTournamentIds(Team team) {
        return team.getTournaments() != null
                ? team.getTournaments().stream().map(t -> t.getId()).collect(Collectors.toList())
                : null;
    }
}

