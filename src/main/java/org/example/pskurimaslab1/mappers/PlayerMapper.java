package org.example.pskurimaslab1.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pskurimaslab1.model.Player;

import java.util.List;

@Mapper
public interface PlayerMapper {
    void insertPlayer(Player player);
    void updatePlayer(Player player);
    void deletePlayer(Long id);
    void changePlayerTeam(@Param("playerId") Long playerId, @Param("teamId") Long teamId);
}
