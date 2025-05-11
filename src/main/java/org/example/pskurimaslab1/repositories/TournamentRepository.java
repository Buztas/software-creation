package org.example.pskurimaslab1.repositories;

import org.example.pskurimaslab1.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    @Modifying
    @Query(value = "DELETE FROM team_tournament WHERE tournament_id = :tournamentId", nativeQuery = true)
    void removeTournamentTeamRelation(Long tournamentId);

    @Query("SELECT t FROM Tournament t JOIN t.teams te WHERE te.id = :teamId")
    List<Tournament> findTournamentsByTeamId(Long teamId);
}
