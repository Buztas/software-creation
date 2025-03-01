package org.example.pskurimaslab1.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

@Mapper
public interface TeamMapper {
    void insertTeam(Team team);
    void addTeamToTournament(@Param("teamId") long teamId, @Param("tournamentId") long tournamentId);
    Team getTeamById(long id);
    List<Team> getAllTeams();
    List<Tournament> getTournamentsByTeamId(long teamId);
}
