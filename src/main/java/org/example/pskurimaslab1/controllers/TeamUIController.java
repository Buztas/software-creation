package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.services.TeamService;
import org.example.pskurimaslab1.services.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/teams")
public class TeamUIController {

    private final TeamService teamService;
    private final TournamentService tournamentService;

    public TeamUIController(TeamService teamService, TournamentService tournamentService) {
        this.teamService = teamService;
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public String getAllTeams(Model model) {
        List<Team> teams = teamService.getTeams();
        model.addAttribute("teams", teams);
        return "teams";
    }

    @GetMapping("/{id}")
    public String getTeamById(@PathVariable Long id, Model model) {
        Team team = teamService.getTeam(id);
        if (team != null) {
            model.addAttribute("team", team);
            return "team-details";
        } else {
            return "error";
        }
    }

    @GetMapping("/add")
    public String showAddTeamForm(Model model) {
        model.addAttribute("team", new Team());
        return "add-team";
    }

    @PostMapping("/add")
    public String addTeam(@ModelAttribute Team team) {
        teamService.addTeam(team);
        return "redirect:/ui/teams";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateTeamForm(@PathVariable Long id, Model model) {
        Team team = teamService.getTeam(id);
        if (team != null) {
            model.addAttribute("team", team);
            return "edit-team";
        }
        return "error";
    }

    @PostMapping("/update/{id}")
    public String updateTeam(@PathVariable Long id, @ModelAttribute Team team) {
        team.setId(id);
        teamService.updateTeam(team);
        return "redirect:/ui/teams";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable Long id) {
        Team team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(team);
        }
        return "redirect:/ui/teams";
    }

    @GetMapping("/{teamId}/tournaments")
    public String getTournamentsByTeam(@PathVariable Long teamId, Model model) {
        List<Tournament> tournaments = tournamentService.getTournamentsByTeam(teamId);
        model.addAttribute("tournaments", tournaments);
        return "team-tournaments";
    }

    @GetMapping("/{teamId}/add-to-tournament/{tournamentId}")
    public String addTeamToTournament(@PathVariable Long teamId, @PathVariable Long tournamentId) {
        teamService.addTeamToTournament(teamId, tournamentId);
        return "redirect:/ui/teams/" + teamId + "/tournaments";
    }
}