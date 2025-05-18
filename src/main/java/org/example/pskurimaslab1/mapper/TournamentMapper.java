package org.example.pskurimaslab1.mapper;

import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.model.dto.TournamentDTO;
import org.example.pskurimaslab1.resolvers.TeamIdResolver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = { TeamIdResolver.class })
public interface TournamentMapper {

    @Mapping(target = "teamIds", expression = "java(mapTeamIds(tournament))")
    TournamentDTO toDto(Tournament tournament);

    @Mapping(source = "teamIds", target = "teams", qualifiedByName = "fromIds")
    Tournament toEntity(TournamentDTO dto);

    default List<Long> mapTeamIds(Tournament tournament) {
        return tournament.getTeams() != null
                ? tournament.getTeams().stream().map(t -> t.getId()).collect(Collectors.toList())
                : null;
    }
}
