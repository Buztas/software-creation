package org.example.pskurimaslab1.repositories;

import org.example.pskurimaslab1.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t FROM Team t JOIN t.tournaments tr WHERE tr.id = :tournamentId")
    List<Team> findTeamsByTournamentId(@Param("tournamentId") Long tournamentId);
}
