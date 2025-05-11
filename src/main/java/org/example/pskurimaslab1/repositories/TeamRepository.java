package org.example.pskurimaslab1.repositories;

import org.example.pskurimaslab1.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    @Query("SELECT t FROM Team t JOIN t.tournaments tt WHERE tt.id = :tournamentId")
    List<Team> findTeamsByTournamentId(Long tournamentId);

    @Modifying
    @Query(value = "INSERT INTO team_tournament (team_id, tournament_id) VALUES (:teamId, :tournamentId)", nativeQuery = true)
    void addTeamToTournament(Long teamId, Long tournamentId);

    @Modifying
    @Query(value = "DELETE FROM player WHERE team_id = :teamId", nativeQuery = true)
    void removePlayersByTeamId(Long teamId);

    @Modifying
    @Query(value = "DELETE FROM team_tournament WHERE team_id = :teamId", nativeQuery = true)
    void removeTeamTournamentRelationship(Long teamId);

    @Modifying
    @Query(value = "UPDATE player SET team_id = NULL WHERE id = :playerId AND team_id = :teamId", nativeQuery = true)
    void removePlayerFromTeam(Long teamId, Long playerId);
}