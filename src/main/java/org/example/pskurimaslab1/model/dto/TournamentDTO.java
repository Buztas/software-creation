package org.example.pskurimaslab1.model.dto;

import java.util.Date;
import java.util.List;

public record TournamentDTO(
        Long id,
        String name,
        String sport,
        Date startDate,
        Date endDate,
        String winner,
        List<Long> teamIds
) {}
