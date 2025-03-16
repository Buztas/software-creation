package org.example.pskurimaslab1.repositories;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Player findPlayerById(Long id);
    List<Player> findPlayersByTeamId(Long teamId);
}
