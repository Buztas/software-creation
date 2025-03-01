package org.example.pskurimaslab1.controllers;

import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.services.PlayerService;
import org.example.pskurimaslab1.services.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ui/players")
public class PlayerUIController {

    private final PlayerService playerService;
    private final TeamService teamService;

    public PlayerUIController(PlayerService playerService, TeamService teamService) {
        this.playerService = playerService;
        this.teamService = teamService;
    }

    @GetMapping
    public String getAllPlayers(Model model) {
        List<Player> players = playerService.getPlayers();
        model.addAttribute("players", players);
        return "players";
    }

    @GetMapping("/{id}")
    public String getPlayerById(@PathVariable long id, Model model) {
        Player player = playerService.getPlayer(id);
        if (player != null) {
            model.addAttribute("player", player);
            return "player-details";
        } else {
            return "error";
        }
    }

    @GetMapping("/add")
    public String showAddPlayerForm(Model model) {
        model.addAttribute("player", new Player());
        List<Team> teams = teamService.getTeams();
        model.addAttribute("teams", teams);
        return "add-player";
    }

    @PostMapping("/add")
    public String addPlayer(@ModelAttribute Player player, @RequestParam Long teamId) {
        Team team = teamService.getTeam(teamId.intValue());
        player.setTeam(team);
        playerService.addPlayer(player);
        return "redirect:/ui/players";
    }

    @GetMapping("/edit/{id}")
    public String showUpdatePlayerForm(@PathVariable long id, Model model) {
        Player player = playerService.getPlayer(id);
        if (player != null) {
            model.addAttribute("player", player);
            List<Team> teams = teamService.getTeams();
            model.addAttribute("teams", teams);
            return "edit-player";
        } else {
            return "error";
        }
    }

    @PostMapping("/edit/{id}")
    public String updatePlayer(@PathVariable long id, @ModelAttribute Player player, @RequestParam Long teamId) {
        Team team = teamService.getTeam(teamId.intValue());
        player.setTeam(team);
        player.setId(id);
        playerService.updatePlayer(player);
        return "redirect:/ui/players";
    }

    @GetMapping("/delete/{id}")
    public String deletePlayer(@PathVariable long id) {
        playerService.removePlayer(id);
        return "redirect:/ui/players";
    }
}