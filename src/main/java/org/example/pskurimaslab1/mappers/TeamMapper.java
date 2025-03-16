package org.example.pskurimaslab1.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

@Mapper
public interface TeamMapper {
    void insertTeam(Team team);
    void updateTeam(Team team);
    void deleteTeam(Long id);
    void addTeamToTournament(@Param("teamId") Long teamId, @Param("tournamentId") Long tournamentId);
    void removeTeamFromTournament(@Param("teamId") Long teamId, @Param("tournamentId") Long tournamentId);
    void removePlayersByTeamId(@Param("teamId") Long teamId);
    void removeTeamTournamentRelationship(@Param("teamId") Long teamId);
}
