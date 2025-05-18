package org.example.pskurimaslab1.resolvers;

import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TournamentIdResolver {
    private final TournamentRepository tournamentRepository;

    public TournamentIdResolver(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Named("fromId")
    public Tournament fromId(Long id) {
        return id == null ? null : tournamentRepository.findById(id).orElse(null);
    }

    @Named("fromIds")
    public List<Tournament> fromIds(List<Long> ids) {
        return ids == null ? List.of() : ids.stream()
                .map(this::fromId)
                .collect(Collectors.toList());
    }
}
