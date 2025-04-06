package org.example.pskurimaslab1.jsfbeans;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.services.TeamService;
import org.example.pskurimaslab1.services.TournamentService;

import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
public class TournamentBean implements Serializable {

    @Inject
    private TournamentService tournamentService;

    @Inject
    private TeamService teamService;

    private Tournament tournament;
    private List<Tournament> tournaments;
    private Long selectedTeamId;
    private List<Team> availableTeams;
    private List<Team> tournamentTeams;

    @PostConstruct
    public void init() {
        tournament = new Tournament();
        loadTournaments();
    }

    public void loadTournamentData(Long tournamentId) {
        if (tournamentId != null) {
            tournament = tournamentService.getTournament(tournamentId);
            loadAvailableTeams();
            loadTeamsInTournament();
        }
    }

    private void loadAvailableTeams() {
        List<Team> allTeams = teamService.getTeams();
        if (tournament != null && tournament.getTeams() != null) {
            // Filter out teams that are already in the tournament
            availableTeams = teamService.getTeams().stream()
                    .filter(team -> team.getTournaments().stream()
                            .noneMatch(existingTournament -> isConflicting(tournament, existingTournament)))
                    .collect(Collectors.toList());
        } else {
            availableTeams = allTeams;
        }
    }

    private void loadTeamsInTournament() {
        List<Team> allTeams = teamService.getTeams();
        if (tournament != null && tournament.getTeams() != null) {
            // Filter out teams that are already in the tournament
            tournamentTeams = tournament.getTeams();
        } else {
            tournamentTeams = allTeams;
        }
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public List<Team> getAvailableTeams() {
        return availableTeams;
    }

    public List<Team> getTournamentTeams() {
        return tournamentTeams;
    }

    public Long getSelectedTeamId() {
        return selectedTeamId;
    }

    public void setSelectedTeamId(Long selectedTeamId) {
        this.selectedTeamId = selectedTeamId;
    }

    public String addTournament() {
        try {
            tournamentService.addTournament(tournament);
            tournament = new Tournament();
            loadTournaments();
            return "tournaments?faces-redirect=true";
        } catch (Exception e) {
            // Handle exception
            return null;
        }
    }

    public String updateTournament() {
        try {
            tournamentService.updateTournament(tournament);
            loadTournaments();
            return "tournaments?faces-redirect=true";
        } catch (Exception e) {
            // Handle exception
            return null;
        }
    }

    public String addTeamToTournament() {
        try {
            teamService.addTeamToTournament(selectedTeamId, tournament.getId());
            // Reload tournament to reflect changes
            tournament = tournamentService.getTournament(tournament.getId());
            loadAvailableTeams();
            return null; // Stay on the same page
        } catch (Exception e) {
            // Handle exception
            return null;
        }
    }

    public String deleteTournament(Tournament tournament) {
        try {
            tournamentService.removeFromTournament(tournament);
            loadTournaments();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String removeTeamFromTournament(Team team, Tournament tournament) {
        try {
            if (tournament != null) { // Ensure the tournament is not null
                // Call the service to remove the team from the tournament
                tournamentService.removeTeamFromTournament(team.getId(), tournament.getId());

                // Reload the tournament and update the teams
                tournament = tournamentService.getTournament(tournament.getId());
                loadAvailableTeams();  // Reload available teams
                loadTeamsInTournament(); // Reload teams in tournament
            }
            return null; // Stay on the same page
        } catch (Exception e) {
            // Handle exception (e.g., log it)
            return null;
        }
    }


    public void loadTournaments() {
        tournaments = tournamentService.getTournaments();
    }

    private boolean isConflicting(Tournament newTournament, Tournament existingTournament) {
        return !(newTournament.getEndDate().before(existingTournament.getStartDate()) ||
                newTournament.getStartDate().after(existingTournament.getEndDate()));
    }
}