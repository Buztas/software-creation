package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.model.Tournament;
import org.example.pskurimaslab1.services.TournamentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/tournaments")
public class TournamentUIController {

    private final TournamentService tournamentService;

    public TournamentUIController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping
    public String getAllTournaments(Model model) {
        List<Tournament> tournaments = tournamentService.getTournaments();
        model.addAttribute("tournaments", tournaments);
        return "tournaments";
    }

    @GetMapping("/{id}")
    public String getTournamentById(@PathVariable long id, Model model) {
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            model.addAttribute("tournament", tournament);
            model.addAttribute("teams", tournamentService.getTeamsByTournamentId(id));
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
    public String showUpdateTournamentForm(@PathVariable long id, Model model) {
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            model.addAttribute("tournament", tournament);
            return "edit-tournament";
        } else {
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateTournament(@PathVariable long id, @ModelAttribute Tournament tournament) {
        tournament.setId(id);
        tournamentService.updateTournament(tournament);
        return "redirect:/ui/tournaments";
    }

    @GetMapping("/delete/{id}")
    public String deleteTournament(@PathVariable long id) {
        Tournament tournament = tournamentService.getTournament(id);
        if (tournament != null) {
            tournamentService.removeFromTournament(tournament);
        }
        return "redirect:/ui/tournaments";
    }

    @GetMapping("/{tournamentId}/teams")
    public String getTeamsByTournament(@PathVariable long tournamentId, Model model) {
        List<Team> teams = tournamentService.getTeamsByTournamentId(tournamentId);
        model.addAttribute("teams", teams);
        return "tournament-teams";
    }
}
