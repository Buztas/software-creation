package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.services.TeamService;
import org.example.pskurimaslab1.services.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/ui/tournaments")
public class TournamentUIController {

    private final TournamentService tournamentService;
    private final TeamService teamService;

    public TournamentUIController(TournamentService tournamentService, TeamService teamService) {
        this.tournamentService = tournamentService;
        this.teamService = teamService;
    }

    @GetMapping
    public String getAllTournaments(Model model) {
        List<Tournament> tournaments = tournamentService.getTournaments();
        model.addAttribute("tournaments", tournaments);
        return "tournaments";
    }

    @GetMapping("/{id}")
    public String getTournamentById(@PathVariable Long id, Model model) {
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            model.addAttribute("tournament", tournament);
            model.addAttribute("teams", teamService.getTeamsByTournamentId(id));
            return "tournament-details";
        } else {
            return "error";
        }
    }

    @GetMapping("/add")
    public String showAddTournamentForm(Model model) {
        model.addAttribute("tournament", new Tournament());
        return "add-tournament";
    }

    @PostMapping("/add")
    public String addTournament(@ModelAttribute Tournament tournament) {
        tournamentService.addTournament(tournament);
        return "redirect:/ui/tournaments";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateTournamentForm(@PathVariable Long id, Model model) {
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            model.addAttribute("tournament", tournament);

            List<Team> allTeams = teamService.getTeams().stream()
                    .filter(team -> team.getTournaments().stream()
                            .noneMatch(existingTournament -> isConflicting(tournament, existingTournament)))
                    .collect(Collectors.toList());
            model.addAttribute("allTeams", allTeams);
            return "edit-tournament";
        } else {
            return "error";
        }
    }

    private boolean isConflicting(Tournament newTournament, Tournament existingTournament) {
        return !(newTournament.getEndDate().isBefore(existingTournament.getStartDate()) ||
                newTournament.getStartDate().isAfter(existingTournament.getEndDate()));
    }

    @PostMapping("/update/{id}")
    public String updateTournament(@PathVariable Long id, @ModelAttribute Tournament tournament) {
        tournament.setId(id); // Ensure the ID is set
        tournamentService.updateTournament(tournament);
        return "redirect:/ui/tournaments"; // Redirect to the tournaments list page
    }

    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable Long id) {
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            tournamentService.removeFromTournament(tournament);
        }
        return "redirect:/ui/tournaments";
    }

    @GetMapping("/{tournamentId}/teams")
    public String getTeamsByTournament(@PathVariable Long tournamentId, Model model) {
        List<Team> teams = teamService.getTeamsByTournamentId(tournamentId);
        model.addAttribute("teams", teams);
        return "tournament-teams";
    }

    @PostMapping("/{tournamentId}/add-team")
    public String addTeamToTournament(@PathVariable Long tournamentId, @RequestParam Long teamId) {
        tournamentService.addTeamToTournament(tournamentId, teamId);
        return "redirect:/ui/tournaments/edit/" + tournamentId;
    }

    @GetMapping("/{tournamentId}/remove-team/{teamId}")
    public String removeTeamFromTournament(@PathVariable Long tournamentId, @PathVariable Long teamId) {
        tournamentService.removeTeamFromTournament(tournamentId, teamId);
        return "redirect:/ui/tournaments/edit/" + tournamentId;
    }
}
