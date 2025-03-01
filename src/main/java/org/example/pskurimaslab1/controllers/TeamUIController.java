package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.services.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/teams")
public class TeamUIController {

    private final TeamService teamService;

    public TeamUIController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public String getAllTeams(Model model) {
        List<Team> teams = teamService.getTeams();
        model.addAttribute("teams", teams);
        return "teams";
    }

    @GetMapping("/{id}")
    public String getTeamById(@PathVariable int id, Model model) {
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
    public String showUpdateTeamForm(@PathVariable int id, Model model) {
        Team team = teamService.getTeam(id);
        if (team != null) {
            model.addAttribute("team", team);
            return "edit-team";
        } else {
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTeam(@PathVariable int id, @ModelAttribute Team team) {
        teamService.updateTeam(team);
        return "redirect:/ui/teams";
    }

    @GetMapping("/delete/{id}")
    public String deleteTeam(@PathVariable int id) {
        Team team = teamService.getTeam(id);
        if (team != null) {
            teamService.deleteTeam(team);
        }
        return "redirect:/ui/teams";
    }

    @GetMapping("/{teamId}/tournaments")
    public String getTournamentsByTeam(@PathVariable long teamId, Model model) {
        List<Tournament> tournaments = teamService.getTournamentsByTeam(teamId);
        model.addAttribute("tournaments", tournaments);
        return "team-tournaments";
    }

    @GetMapping("/{teamId}/add-to-tournament/{tournamentId}")
    public String addTeamToTournament(@PathVariable long teamId, @PathVariable long tournamentId) {
        teamService.addTeamToTournament(teamId, tournamentId);
        return "redirect:/ui/teams/" + teamId + "/tournaments";
    }
}