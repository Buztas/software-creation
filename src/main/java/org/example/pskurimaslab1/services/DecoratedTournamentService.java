package org.example.pskurimaslab1.services;

import org.example.pskurimaslab1.mapper.TournamentMapper;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.model.dto.TournamentDTO;
import org.example.pskurimaslab1.repositories.TeamRepository;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Profile("prod")
// CDI Decorator and Alternative
public class DecoratedTournamentService extends DefaultTournamentService {
    public DecoratedTournamentService(TournamentRepository tournamentRepository, TeamRepository teamRepository, TournamentMapper tournamentMapper) {
        super(tournamentRepository, teamRepository, tournamentMapper);
    }

    @Override
    public TournamentDTO addTournament(TournamentDTO tournament) {
        if (tournament.startDate().after(tournament.endDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        return super.addTournament(tournament);
    }

    @Override
    @Transactional
    public TournamentDTO updateTournament(TournamentDTO tournament) {
        if(tournament.startDate().after(tournament.endDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
        return super.updateTournament(tournament);
    }
}
