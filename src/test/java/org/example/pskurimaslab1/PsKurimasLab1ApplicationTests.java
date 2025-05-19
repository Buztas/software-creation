package org.example.pskurimaslab1;

import org.example.pskurimaslab1.mapper.TournamentMapper;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.model.dto.TournamentDTO;
import org.example.pskurimaslab1.repositories.TournamentRepository;
import org.example.pskurimaslab1.services.TournamentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;

import java.time.Instant;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.List;

@SpringBootTest
class PsKurimasLab1ApplicationTests {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Autowired
    private TournamentMapper tournamentMapper;

    @Autowired
    private TournamentService tournamentService;

    @Test
    void shouldThrowOptimisticLockException() {
        TournamentDTO created = tournamentService.addTournament(new TournamentDTO(
                null, "Optimistic Test", "eSport",
                Date.from(Instant.now()),
                Date.from(Instant.now().plusSeconds(3600)),
                "Winner",
                List.of(),
                null
        ));

        TournamentDTO t1 = tournamentMapper.toDto(tournamentRepository.findById(created.id()).orElseThrow());
        TournamentDTO t2 = tournamentMapper.toDto(tournamentRepository.findById(created.id()).orElseThrow());

        t1 = new TournamentDTO(t1.id(), "User1 Update", t1.sport(), t1.startDate(), t1.endDate(), t1.winner(), t1.teamIds(), t1.version());
        tournamentService.updateTournament(t1);

        TournamentDTO staleUpdate = new TournamentDTO(t2.id(), "User2 Update", t2.sport(), t2.startDate(), t2.endDate(), t2.winner(), t2.teamIds(), t2.version());

//        tournamentService.updateTournament(staleUpdate);
        Assertions.assertThrows(ObjectOptimisticLockingFailureException.class, () -> {
            tournamentService.updateTournament(staleUpdate);
        });
    }

}
//package org.example.pskurimaslab1.model.dto;
//
//import java.util.Date;
//import java.util.List;
//
//public record TournamentDTO(
//        Long id,
//        String name,
//        String sport,
//        Date startDate,
//        Date endDate,
//        String winner,
//        List<Long> teamIds,
//        Long version
//) {}
