package org.example.pskurimaslab1.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

@Mapper
public interface TournamentMapper {
    void insertTournament(Tournament tournament);
    Tournament getTournament(long id);
    List<Tournament> getTournaments();
    List<Team> getAllTeamsByTournamentId(long tournamentId);
}
