package org.example.pskurimaslab1.model.dto;

import java.util.List;

public record TeamDTO(
        Long id,
        String name,
        String sport,
        List<Long> playerIds,
        List<Long> tournamentIds
) {}