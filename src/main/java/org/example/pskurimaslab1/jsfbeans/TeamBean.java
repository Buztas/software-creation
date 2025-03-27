package org.example.pskurimaslab1.jsfbeans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TeamBean implements Serializable {

    @Autowired
    private TeamService teamService;

    private Long teamId;
    private Team team = new Team();
    private List<Team> teams; // Holds the list of teams

    @PostConstruct
    public void init() {
        teams = teamService.getTeams(); // Load teams initially
    }

    public void loadTeam() {
        System.out.println("TeamId received: " + teamId);
        if (teamId != null) {
            team = teamService.getTeam(teamId);
            System.out.println("Loaded team: " + team);
        }
    }

    public void addTeam() {
        teamService.addTeam(team);
        teams = teamService.getTeams(); // Refresh list
        team = new Team(); // Reset form
    }

    public void updateTeam() {
        if (team != null && team.getId() != null) {
            teamService.updateTeam(team);
            teams = teamService.getTeams(); // Refresh list
        }
    }

    public void deleteTeam(Team team) {
        teamService.deleteTeam(team);
        teams = teamService.getTeams(); // Refresh list
    }

    // Getters and Setters
    public Long getTeamId() { return teamId; }
    public void setTeamId(Long teamId) { this.teamId = teamId; }
    public Team getTeam() { return team; }
    public List<Team> getTeams() { return teams; }
}
