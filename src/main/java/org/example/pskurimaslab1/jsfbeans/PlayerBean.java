package org.example.pskurimaslab1.jsfbeans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.example.pskurimaslab1.model.Player;
import org.example.pskurimaslab1.model.Team;
import org.example.pskurimaslab1.services.PlayerService;
import org.example.pskurimaslab1.services.TeamService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@ViewScoped
@Named
public class PlayerBean implements Serializable {

    @Inject
    private PlayerService playerService;

    @Inject
    private TeamService teamService;

    private Player player;
    private List<Player> players;
    private List<Team> teams;
    private Long selectedTeamId;

    @PostConstruct
    public void init() {
        player = new Player();
        loadPlayers();
        loadTeams();

        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();
        String playerId = params.get("id");

        if (playerId != null && !playerId.isEmpty()) {
            loadPlayerData(Long.valueOf(playerId));
        }
    }

    public void loadPlayerData(Long playerId) {
        player = playerService.getPlayer(playerId);
        if (player != null && player.getTeam() != null) {
            selectedTeamId = player.getTeam().getId();
        } else {
            selectedTeamId = null;
        }
    }
    private void loadPlayers() {
        players = playerService.getPlayers();
    }

    private void loadTeams() {
        teams = teamService.getTeams();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Long getSelectedTeamId() {
        return selectedTeamId;
    }

    public void setSelectedTeamId(Long selectedTeamId) {
        this.selectedTeamId = selectedTeamId;
    }

    public String addPlayer() {
        try {
            if (selectedTeamId != null) {
                Team team = new Team();
                team.setId(selectedTeamId);
                player.setTeam(team);
            }

            playerService.addPlayer(player);
            player = new Player();
            selectedTeamId = null;
            loadPlayers();
            return "players?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }

    public String updatePlayer() {
        try {
            if (selectedTeamId != null) {
                Team team = new Team();
                team.setId(selectedTeamId);
                player.setTeam(team);
            }

            playerService.updatePlayer(player);
            player = new Player();
            selectedTeamId = null;
            loadPlayers();
            return "players?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }

    public String deletePlayer(Player player) {
        try {
            playerService.removePlayer(player.getId());
            loadPlayers();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public String editPlayer(Player player) {
        return "edit-player?faces-redirect=true&id=" + player.getId();
    }

    public String viewPlayerDetails(Long playerId) {
        return "player-details?faces-redirect=true&id=" + playerId;
    }
}