package org.example.pskurimaslab1.resolvers;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeamIdResolver {
    private final TeamRepository teamRepository;

    public TeamIdResolver(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @Named("fromId")
    public Team fromId(Long id) {
        return id == null ? null : teamRepository.findById(id).orElse(null);
    }

    @Named("fromIds")
    public List<Team> fromIds(List<Long> ids) {
        return ids == null ? List.of() : ids.stream()
                .map(this::fromId)
                .collect(Collectors.toList());
    }
}
