package org.example.pskurimaslab1.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;

import java.util.List;

@Mapper
public interface TournamentMapper {
    void insertTournament(Tournament tournament);
    void updateTournament(Tournament tournament);
    void deleteTournament(Long id);
    void removeTournamentTeamRelation(Long tournamentId);
}