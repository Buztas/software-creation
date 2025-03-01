package org.example.pskurimaslab1.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.example.pskurimaslab1.model.Player;

import java.util.List;

@Mapper
public interface PlayerMapper {
    void insertPlayer(Player player);
    Player getPlayerById(long id);
    List<Player> getAllPlayers();
    List<Player> getPlayersByTeamId(long id);
}
