package org.example.pskurimaslab1.repositories;

import org.example.pskurimaslab1.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
    @Query("SELECT t FROM Tournament t JOIN t.teams tm WHERE tm.id = :teamId")
    List<Tournament> findTournamentsByTeamId(@Param("teamId") Long teamId);
}
